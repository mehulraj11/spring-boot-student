package com.example.student.repository;

import com.example.student.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDocumentRepository extends JpaRepository<StudentDocument, Long> {
}
