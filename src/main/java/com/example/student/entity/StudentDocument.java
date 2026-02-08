package com.example.student.entity;

import com.example.student.enums.DocumentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class StudentDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentDocumentId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    private String fileName;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private LocalDateTime uploadedAt;
    public StudentDocument(){}

    public StudentDocument(Long studentDocumentId,
                           Student student,
                           String fileName,
                           String filePath,
                           DocumentStatus status,
                           LocalDateTime uploadedAt
    ) {
        this.studentDocumentId = studentDocumentId;
        this.student = student;
        this.fileName = fileName;
        this.filePath = filePath;
        this.status = status;
        this.uploadedAt = uploadedAt;
    }

    public Long getStudentDocumentId() {
        return studentDocumentId;
    }

    public void setStudentDocumentId(Long studentDocumentId) {
        this.studentDocumentId = studentDocumentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
