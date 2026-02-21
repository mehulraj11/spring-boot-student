package com.example.student.controller;


import com.example.student.dto.StudentDocumentDto;
import com.example.student.entity.StudentDocument;
import com.example.student.service.StudentDocumentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@PreAuthorize("hasAnyRole('STUDENT')")
@RequestMapping("/students-documents")
public class StudentDocumentController {
    private final StudentDocumentService studentDocumentService;
    public StudentDocumentController(StudentDocumentService studentDocumentService){
        this.studentDocumentService = studentDocumentService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('STUDENT')")
    public StudentDocumentDto upload(
            @RequestParam("file")MultipartFile file
            ) throws IOException
    {
        StudentDocument document = studentDocumentService.uploadDocument(file);
        return new StudentDocumentDto(
                document.getStudentDocumentId(),
                document.getFileName(),
                document.getStatus()
        );
    }
}
