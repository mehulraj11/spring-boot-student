package com.example.student.service.implementation;

import com.example.student.dto.AddStudentDto;
import com.example.student.dto.StudentDto;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentServie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentImplementation implements StudentServie {
    private final StudentRepository studentRepository;

    public StudentImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student -> new StudentDto(
                        student.getId(),
                        student.getName(),
                        student.getRegistration_no(),
                        student.getEmail(),
                        student.getCourse()))
                .toList();
    }

    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("student not found with id"));
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getRegistration_no(),
                student.getEmail(),
                student.getCourse());
    }

    public StudentDto createNewStudent(AddStudentDto addStudentDto) {
        Student newStudent = new Student();
        newStudent.setName(addStudentDto.getName());
        newStudent.setCourse(addStudentDto.getCourse());
        newStudent.setEmail(addStudentDto.getEmail());
        newStudent.setRegistration_no(addStudentDto.getRegistration_no());
        Student student = studentRepository.save(newStudent);
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getRegistration_no(),
                student.getEmail(),
                student.getCourse()
        );
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("student not found on delete"));
        studentRepository.delete(student);
    }

    public StudentDto updateStudent(Long id, AddStudentDto addStudentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student doesn't exist"));

        student.setName(addStudentDto.getName());
        student.setRegistration_no(addStudentDto.getRegistration_no());
        student.setEmail(addStudentDto.getEmail());
        student.setCourse(addStudentDto.getCourse());

        Student updatedStudent = studentRepository.save(student);

        return new StudentDto(
                updatedStudent.getId(),
                updatedStudent.getName(),
                updatedStudent.getRegistration_no(),
                updatedStudent.getEmail(),
                updatedStudent.getCourse()
        );
    }

    public StudentDto patchStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("error in patch"));
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
                updated.getId(),
                updated.getName(),
                updated.getRegistration_no(),
                updated.getEmail(),
                updated.getCourse()
        );
    }
}
