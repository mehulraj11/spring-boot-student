package com.example.student.service.implementation;

import com.example.student.dto.ScholarshipApplicationDto;
import com.example.student.entity.Scholarship;
import com.example.student.entity.ScholarshipApplication;
import com.example.student.entity.Student;
import com.example.student.entity.Users;
import com.example.student.enums.ApplicationStatus;
import com.example.student.exception.StudentException;
import com.example.student.repository.ScholarshipApplicationRepository;
import com.example.student.repository.ScholarshipRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.UserRepository;
import com.example.student.service.ScholarshipApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class ScholarshipApplicationImpl implements ScholarshipApplicationService {
    private final StudentRepository studentRepository;
    private final ScholarshipRepository scholarshipRepository;

    private final ScholarshipApplicationRepository scholarshipApplicationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScholarshipApplicationImpl(StudentRepository studentRepository,
                                      ScholarshipRepository scholarshipRepository,
                                      ScholarshipApplicationRepository scholarshipApplicationRepository,
                                      UserRepository userRepository){
        this.studentRepository = studentRepository;
        this.scholarshipRepository = scholarshipRepository;
        this.scholarshipApplicationRepository = scholarshipApplicationRepository;
        this.userRepository = userRepository;
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

    @Override
    public void verfiyApplication(Long scholarshipApplicationId, ApplicationStatus status) {
        Authentication auth = new SecurityContextHolder().getContext().getAuthentication();
        String email = auth.getName();
        Users loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
        ScholarshipApplication application = scholarshipApplicationRepository.findById(scholarshipApplicationId)
                .orElseThrow(()-> new IllegalArgumentException("application not found"));

        application.setStatus(status);
        application.setVerifiedAt(LocalDateTime.now());
        application.setRemarks("ok");
        application.setVerifier(loggedInUser);
        scholarshipApplicationRepository.save(application);
        log.info("Application with id {} has been verified as {}",scholarshipApplicationId, status);
    }

}
