package com.example.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scholarshipId;

    private String title;

    private String eligibility;
    private Double amount;
    private boolean active;
//private List<ScholarshipApplication> scholarshipApplicationList = new ArrayList<>();
//
//    public List<ScholarshipApplication> getScholarshipApplicationList() {
//        return scholarshipApplicationList;
//    }
//
//    public void setScholarshipApplicationList(List<ScholarshipApplication> scholarshipApplicationList) {
//        this.scholarshipApplicationList = scholarshipApplicationList;
//    }

    public Scholarship(){}
    public Scholarship(Long scholarshipId,
                       String title,
                       String eligibility,
                       Double amount,
                       boolean active
//                       List<ScholarshipApplication> scholarshipApplicationList
    )
    {
        this.scholarshipId = scholarshipId;
        this.title = title;
        this.eligibility = eligibility;
        this.amount = amount;
        this.active = active;
//        this.scholarshipApplicationList = scholarshipApplicationList;
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
