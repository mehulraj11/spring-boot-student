package com.example.student.service.implementation;

import com.example.student.dto.AddStudentDto;
import com.example.student.dto.StudentDto;
import com.example.student.entity.Student;
import com.example.student.exception.StudentException;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentServie;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentImpl implements StudentServie {
    private final StudentRepository studentRepository;

    public StudentImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student -> new StudentDto(
                        student.getName(),
                        student.getRegistration_no(),
                        student.getEmail(),
                        student.getCourse()))
                .toList();
    }

    @Override
    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        return new StudentDto(
                student.getName(),
                student.getRegistration_no(),
                student.getEmail(),
                student.getCourse());
    }

    @Override
    public StudentDto createNewStudent(AddStudentDto addStudentDto) {
        Student newStudent = new Student();
        newStudent.setName(addStudentDto.getName());
        newStudent.setCourse(addStudentDto.getCourse());
        newStudent.setEmail(addStudentDto.getEmail());
        newStudent.setRegistration_no(addStudentDto.getRegistration_no());
        Student student = studentRepository.save(newStudent);
        return new StudentDto(
                student.getName(),
                student.getRegistration_no(),
                student.getEmail(),
                student.getCourse()
        );
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        studentRepository.delete(student);
    }

    @Override
    public StudentDto updateStudent(Long id, AddStudentDto addStudentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(id));

        student.setName(addStudentDto.getName());
        student.setRegistration_no(addStudentDto.getRegistration_no());
        student.setEmail(addStudentDto.getEmail());
        student.setCourse(addStudentDto.getCourse());

        Student updatedStudent = studentRepository.save(student);

        return new StudentDto(
                updatedStudent.getName(),
                updatedStudent.getRegistration_no(),
                updatedStudent.getEmail(),
                updatedStudent.getCourse()
        );
    }

    @Override
    public StudentDto patchStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> student.setName((String) value);
                case "registration_no" -> student.setRegistration_no((String) value);
                case "email" -> student.setEmail((String) value);
                case "course" -> student.setCourse((String) value);
            }
        });
        Student updated = studentRepository.save(student);
        return new StudentDto(
                updated.getName(),
                updated.getRegistration_no(),
                updated.getEmail(),
                updated.getCourse()
        );
    }
}
