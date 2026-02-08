package com.example.student.service.implementation;

import ch.qos.logback.core.util.StringUtil;
import com.example.student.service.StorageService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public String store(MultipartFile file) throws IOException, java.io.IOException {

        if(file.isEmpty()) throw new IllegalArgumentException("file is empty");

        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")
            && !contentType.equals("application/pdf")
        ) throw new IllegalArgumentException("file type is not valid");

        Path uplaodPath = Paths.get(uploadDir);
        Files.createDirectories(uplaodPath);

        String fileName = UUID.randomUUID() + "_"+ StringUtils.cleanPath(file.getOriginalFilename());

        Path filePath = uplaodPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }
}
