package com.example.student.service;

import com.example.student.entity.StudentDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentDocumentService {
    public StudentDocument uploadDocument(Long studentId, MultipartFile file) throws IOException;
}
