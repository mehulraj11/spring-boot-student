package com.example.student.dto;

import jakarta.validation.constraints.NotBlank;

public class ScholarshipDto {
    private Long scholarshipId;

    @NotBlank(message = "title required")
    private String title;

    @NotBlank(message = "eligibility required")
    private String eligibility;
    private Double amount;
    private boolean active;

    public ScholarshipDto( String title, String eligibility, Double amount, boolean active) {
        this.title = title;
        this.eligibility = eligibility;
        this.amount = amount;
        this.active = active;
    }

    public Long getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(Long scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
