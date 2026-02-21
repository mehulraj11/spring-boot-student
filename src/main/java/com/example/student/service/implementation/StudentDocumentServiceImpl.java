package com.example.student.service.implementation;

import com.example.student.entity.Student;
import com.example.student.entity.StudentDocument;
import com.example.student.enums.DocumentStatus;
import com.example.student.repository.StudentDocumentRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.UserRepository;
import com.example.student.service.StorageService;
import com.example.student.service.StudentDocumentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
            StorageService storageService,
            UserRepository userRepository){
        this.studentRepository = studentRepository;
        this.studentDocumentRepository = studentDocumentRepository;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public StudentDocument uploadDocument(MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email  = auth.getName();
        Student student = studentRepository.findByUsers_Email(email);

        String fileName = storageService.uploadFile(file);
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
