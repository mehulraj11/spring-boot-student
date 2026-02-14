package com.example.student.service.implementation;

import com.example.student.service.StorageService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) throws IOException, java.io.IOException {

        if(file.isEmpty()) throw new IllegalArgumentException("file is empty");

        String contentType = file.getContentType();
        if (contentType != null && !contentType.startsWith("image/")
            && !contentType.equals("application/pdf")
        ) throw new IllegalArgumentException("file type is not valid");

        Path uplaodPath = Paths.get(uploadDir);
        Files.createDirectories(uplaodPath);

        String fileName = UUID.randomUUID() + "_"+ StringUtils.cleanPath(file.getOriginalFilename());

        Path filePath = uplaodPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

    @Override
    public List<String[]> uploadCsv(MultipartFile file) throws IOException, java.io.IOException {
        if (file.isEmpty()) throw new IllegalArgumentException("csv is empty");
        if(!file.getOriginalFilename().endsWith(".csv")) throw new IllegalArgumentException("only csv allowed");

        List<String[]> rows = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null){
                if (isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                rows.add(line.split(","));
            }
        }
        return rows;
    }
}
