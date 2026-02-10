package com.example.student.service;

import com.example.student.dto.ScholarshipDto;
import com.example.student.entity.Scholarship;

import java.util.List;

public interface ScholarshipService {
    List<ScholarshipDto> getAll(
            int page,
            int size,
            String sortBy,
            String order);
    Scholarship getById(Long id);
    Scholarship create(Scholarship scholarship);
    Scholarship updateScholarship(Long id);
    void deleteScholarShip(Long id);
}
