package com.example.student.service;

import com.example.student.dto.AddStudentDto;
import com.example.student.dto.StudentDto;

import java.util.List;
import java.util.Map;

public interface StudentServie {
    List<StudentDto> getAllStudents();

    StudentDto getStudent(Long id);

    StudentDto createNewStudent(AddStudentDto addStudentDto);

    void deleteStudent(Long id);

    StudentDto updateStudent(Long id, AddStudentDto addStudentDto);

    StudentDto patchStudent(Long id, Map<String, Object> updates);
}
