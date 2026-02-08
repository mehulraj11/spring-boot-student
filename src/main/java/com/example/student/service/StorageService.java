package com.example.student.service;

import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file) throws IOException, java.io.IOException;
}
