package com.example.student.repository;

import com.example.student.entity.Student;
import com.example.student.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByUsers_UserId(Long userId);
    Student findByUsers_Email(String email);
}
