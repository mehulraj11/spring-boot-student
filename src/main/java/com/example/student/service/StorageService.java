package com.example.student.service;

import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    String uploadFile(MultipartFile file) throws IOException, java.io.IOException;
    List<String>  uploadCsv(MultipartFile file) throws IOException, java.io.IOException;
}
