package com.example.student.service.implementation;

import com.example.student.service.StorageService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("file is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.startsWith("image/") &&
                        !contentType.equals("application/pdf"))) {

            throw new IllegalArgumentException("file type is not valid");
        }
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" +
                StringUtils.cleanPath(file.getOriginalFilename());

        File destinationFile = new File(uploadDirFile, fileName);
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }
    @Override
    public List<String> uploadCsv(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("CSV is empty");
        }
        if (!"text/csv".equals(file.getContentType())){
            throw new IllegalArgumentException("Only CSV allowed");
        }
        List<String> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                rows.add(line);
            }
        }
        return rows;
    }

}
