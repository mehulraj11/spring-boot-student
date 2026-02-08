package com.example.student.entity;

import com.example.student.enums.ApplicationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "scholarship_applications",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"student_id", "scholarship_id"}
        )
)

public class ScholarshipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scholarshipApplicationId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "scholarship_id", nullable = false)
    private Scholarship scholarship;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    private LocalDateTime verifiedAt;

    @ManyToOne
    @JoinColumn(name = "verified_by")
    private Users verifier;

    private String remarks;

    public ScholarshipApplication(){}

    public ScholarshipApplication(Long scholarshipApplicationId,
                                  Student student,
                                  Scholarship scholarship,
                                  ApplicationStatus status,
                                  LocalDateTime appliedAt,
                                  LocalDateTime verifiedAt,
                                  Users verifier,
                                  String remarks)
    {
        this.scholarshipApplicationId = scholarshipApplicationId;
        this.student = student;
        this.scholarship = scholarship;
        this.status = status;
        this.appliedAt = appliedAt;
        this.verifiedAt = verifiedAt;
        this.verifier = verifier;
        this.remarks = remarks;
    }

    public Long getScholarshipApplicationId() {
        return scholarshipApplicationId;
    }

    public void setScholarshipApplicationId(Long scholarshipApplicationId) {
        this.scholarshipApplicationId = scholarshipApplicationId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public void setScholarship(Scholarship scholarship) {
        this.scholarship = scholarship;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Users getVerifier() {
        return verifier;
    }

    public void setVerifier(Users verifier) {
        this.verifier = verifier;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
