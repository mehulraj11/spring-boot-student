package com.example.student.dto;

import com.example.student.enums.ApplicationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScholarshipApplicationDto {

    private Long applicationId;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    private Long studentId;
    private String studentName;
    private LocalDate dob;

    private String email;

    private Long scholarshipId;
    private String scholarshipTitle;
    private Double amount;

    public ScholarshipApplicationDto(
            Long applicationId,
            ApplicationStatus status,
            LocalDateTime appliedAt,

            Long studentId,
            String studentName,
            LocalDate dob,

            String email,

            Long scholarshipId,
            String scholarshipTitle,
            Double amount
    ) {
        this.applicationId = applicationId;
        this.status = status;
        this.appliedAt = appliedAt;
        this.studentId = studentId;
        this.studentName = studentName;
        this.dob = dob;
        this.email = email;
        this.scholarshipId = scholarshipId;
        this.scholarshipTitle = scholarshipTitle;
        this.amount = amount;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public Long getScholarshipId() {
        return scholarshipId;
    }

    public String getScholarshipTitle() {
        return scholarshipTitle;
    }

    public Double getAmount() {
        return amount;
    }
}
