package com.example.student.service;

import com.example.student.dto.ScholarshipApplicationDto;
import com.example.student.enums.ApplicationStatus;

import java.util.List;

public interface ScholarshipApplicationService {
    void apply(Long studentId, Long scholarshipId);
    List<ScholarshipApplicationDto> getPendingApplications();
    void verfiyApplication(Long scholarshipApplicationId, ApplicationStatus status);
}
