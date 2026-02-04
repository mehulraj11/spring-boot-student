package com.example.student.exception;

public class StudentException extends RuntimeException {
    public StudentException(Long id) {
        super("Student not found with id: "+ id);
    }
}
