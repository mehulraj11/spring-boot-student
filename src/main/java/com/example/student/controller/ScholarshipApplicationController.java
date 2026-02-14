package com.example.student.controller;

import com.example.student.dto.ScholarshipApplicationDto;
import com.example.student.enums.ApplicationStatus;
import com.example.student.service.ScholarshipApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scholarship-application")
public class ScholarshipApplicationController {
    private final ScholarshipApplicationService scholarshipApplicationService;

    public ScholarshipApplicationController(ScholarshipApplicationService scholarshipApplicationService) {
        this.scholarshipApplicationService = scholarshipApplicationService;
    }

    @PostMapping("/apply/{studentId}/{scholarshipId}")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<Void> apply(@PathVariable Long studentId,
                                      @PathVariable Long scholarshipId) {

        scholarshipApplicationService.apply(studentId, scholarshipId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('TEACHER')")
    public ResponseEntity<List<ScholarshipApplicationDto>> getPendingApplications() {
        return ResponseEntity.ok(
                scholarshipApplicationService.getPendingApplications()
        );
    }
    @PatchMapping("/verify/{scholarshipApplicationId}")
    @PreAuthorize("hasAnyRole('TEACHER')")
    public ResponseEntity<String> applicationVerification(@PathVariable Long scholarshipApplicationId, @RequestParam ApplicationStatus status)
    {
        scholarshipApplicationService.verfiyApplication(scholarshipApplicationId, status);
        return ResponseEntity.ok("application verified");
    }
}
