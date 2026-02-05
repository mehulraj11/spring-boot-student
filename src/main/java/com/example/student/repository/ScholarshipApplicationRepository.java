package com.example.student.repository;

import com.example.student.dto.ScholarshipApplicationDto;
import com.example.student.entity.ScholarshipApplication;
import com.example.student.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScholarshipApplicationRepository extends JpaRepository<ScholarshipApplication, Long> {
    @Query("""
    SELECT new com.example.student.dto.ScholarshipApplicationDto(
        sa.id,
        sa.status,
        sa.appliedAt,

        s.id,
        s.name,
        s.dob,

        u.email,

        sc.id,
        sc.title,
        sc.amount
    )
    FROM ScholarshipApplication sa
    JOIN sa.student s
    JOIN s.users u
    JOIN sa.scholarship sc
    WHERE sa.status = :status
""")
    List<ScholarshipApplicationDto> findApplicationsByStatus(
            ApplicationStatus status
    );
}
