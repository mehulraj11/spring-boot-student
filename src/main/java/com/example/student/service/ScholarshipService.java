package com.example.student.service;

import com.example.student.dto.ScholarshipDto;
import com.example.student.entity.Scholarship;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    List<ScholarshipDto> addScholarships(List<ScholarshipDto> scholarshipDtos);
    void uploadCsvFile(MultipartFile file) throws IOException;
}
