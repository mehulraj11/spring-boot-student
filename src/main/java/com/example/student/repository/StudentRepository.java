package com.example.student.repository;

import com.example.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Integer> l = Arrays.asList(1, 2, 3, 3);
    List<Integer> i = l.stream().filter(x -> x % 2 == 0).toList();
}
