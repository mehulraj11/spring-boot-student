package com.example.student.service;

import com.example.student.dto.ScholarshipApplicationDto;

import java.util.List;

public interface ScholarshipApplicationService {
    public void apply(Long studentId, Long scholarshipId);
    List<ScholarshipApplicationDto> getPendingApplications();
}
