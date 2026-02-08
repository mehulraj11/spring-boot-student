package com.example.student.service.implementation;

import com.example.student.dto.ScholarshipApplicationDto;
import com.example.student.entity.Scholarship;
import com.example.student.entity.ScholarshipApplication;
import com.example.student.entity.Student;
import com.example.student.enums.ApplicationStatus;
import com.example.student.exception.StudentException;
import com.example.student.repository.ScholarshipApplicationRepository;
import com.example.student.repository.ScholarshipRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.service.ScholarshipApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class ScholarshipApplicationImpl implements ScholarshipApplicationService {
    private final StudentRepository studentRepository;
    private final ScholarshipRepository scholarshipRepository;

    private final ScholarshipApplicationRepository scholarshipApplicationRepository;
    @Autowired
    public ScholarshipApplicationImpl(StudentRepository studentRepository,
                                      ScholarshipRepository scholarshipRepository,
                                      ScholarshipApplicationRepository scholarshipApplicationRepository
    ){
        this.studentRepository = studentRepository;
        this.scholarshipRepository = scholarshipRepository;
        this.scholarshipApplicationRepository = scholarshipApplicationRepository;
    }

    @Override
    public void apply(Long studentId, Long scholarshipId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new StudentException(studentId));
        Scholarship scholarship = scholarshipRepository.findById(scholarshipId)
                .orElseThrow(()-> new IllegalArgumentException("scholarship not found"));

        ScholarshipApplication scholarshipApplication = new ScholarshipApplication();
        scholarshipApplication.setStudent(student);
        scholarshipApplication.setScholarship(scholarship);
        scholarshipApplication.setStatus(ApplicationStatus.APPLIED);
        scholarshipApplication.setAppliedAt(LocalDateTime.now());
        log.info("{} has applied for scholarship with id{}",student.getName(), scholarship.getScholarshipId());

        scholarshipApplicationRepository.save(scholarshipApplication);
    }

    @Override
    public List<ScholarshipApplicationDto> getPendingApplications() {
        log.info("teacher is getting pending applications for verification");
        return scholarshipApplicationRepository.findApplicationsByStatus(
                ApplicationStatus.APPLIED
        );
    }

}
