package com.example.student.dto;

import com.example.student.enums.DocumentStatus;

public class StudentDocumentDto {
    private Long documentId;
    private String fileName;
    private DocumentStatus status;

    public StudentDocumentDto(Long documentId, String fileName, DocumentStatus status) {
        this.documentId = documentId;
        this.fileName = fileName;
        this.status = status;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public String getFileName() {
        return fileName;
    }

    public DocumentStatus getStatus() {
        return status;
    }
}
