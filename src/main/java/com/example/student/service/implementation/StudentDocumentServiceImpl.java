package com.example.student.service.implementation;

import com.example.student.entity.Student;
import com.example.student.entity.StudentDocument;
import com.example.student.enums.DocumentStatus;
import com.example.student.repository.StudentDocumentRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StorageService;
import com.example.student.service.StudentDocumentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
@Slf4j
@Service
public class StudentDocumentServiceImpl implements StudentDocumentService {
    private final StudentRepository studentRepository;
    private final StudentDocumentRepository studentDocumentRepository;
    private final StorageService storageService;

    @Autowired
    public StudentDocumentServiceImpl(
            StudentRepository studentRepository,
            StudentDocumentRepository studentDocumentRepository,
            StorageService storageService
    ){
        this.studentRepository = studentRepository;
        this.studentDocumentRepository = studentDocumentRepository;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public StudentDocument uploadDocument(Long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String fileName = storageService.store(file);

        StudentDocument document = new StudentDocument();
        document.setStudent(student);
        document.setFileName(fileName);
        document.setFilePath("uploads/" + fileName);
        document.setStatus(DocumentStatus.UPLOADED);
        document.setUploadedAt(LocalDateTime.now());
        log.info("{} has uploaded document",student.getName());
        return studentDocumentRepository.save(document);
    }
}
