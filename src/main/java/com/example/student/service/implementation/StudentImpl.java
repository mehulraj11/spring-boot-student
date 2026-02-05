package com.example.student.service.implementation;

import com.example.student.dto.AddStudentDto;
import com.example.student.dto.StudentDto;
import com.example.student.entity.Role;
import com.example.student.entity.Student;
import com.example.student.entity.Users;
import com.example.student.exception.CreateStudentException;
import com.example.student.exception.StudentException;
import com.example.student.exception.UnauthorizedAccess;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.UserRepository;
import com.example.student.service.StudentServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class StudentImpl implements StudentServie {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    @Autowired
    public StudentImpl(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student -> new StudentDto(
                        student.getName(),
                        student.getDob()))
                .toList();
    }

    @Override
    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        return new StudentDto(
                student.getName(),
                student.getDob());
    }

    @Override
    public StudentDto createNewStudent(AddStudentDto addStudentDto) throws CreateStudentException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUserEmail = auth.getName();

        Users loggedInUser = userRepository.findByEmail(authUserEmail)
                .orElseThrow(()->new IllegalArgumentException("not found"));
        log.info("not able to find user : {}", authUserEmail);
        Users targetUser;
        if (loggedInUser.getRole() == Role.STUDENT) {
            targetUser = loggedInUser;
        }else{
            if (addStudentDto.getUserId() == null) {
                throw new CreateStudentException("userId is required for admin/verifier");
            }
            targetUser = userRepository.findById(addStudentDto.getUserId())
                    .orElseThrow(() -> new CreateStudentException("Target user not found"));
        }
        if (studentRepository.existsByUsersId(targetUser.getId())) {
            throw new CreateStudentException("Student profile already exists");
        }
        Student student = new Student();
        student.setUsers(targetUser);
        student.setName(addStudentDto.getName());
        student.setDob(addStudentDto.getDob());
        Student createdStudent = studentRepository.save(student);
        return new StudentDto(
                createdStudent.getName(),
                createdStudent.getDob()
        );
    }

    @Override
    public void deleteStudent(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = auth.getName();
        Users loggedInUser = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(()-> new IllegalArgumentException("email not found"));
        if(loggedInUser.getRole() != Role.ADMIN){
            log.info("unauthorized access : {}", loggedInUser.getEmail());
            throw new UnauthorizedAccess("only admins allowed");
        }
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        studentRepository.delete(student);
    }

    @Override
    public StudentDto updateStudent(Long id, AddStudentDto addStudentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(id));

        student.setName(addStudentDto.getName());
        student.setDob(addStudentDto.getDob());

        Student updatedStudent = studentRepository.save(student);

        return new StudentDto(
                updatedStudent.getName(),
                updatedStudent.getDob()
        );
    }

    @Override
    public StudentDto patchStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> student.setName((String) value);
                case "dob" -> student.setDob((LocalDate) value);
            }
        });
        Student updated = studentRepository.save(student);
        return new StudentDto(
                updated.getName(),
                updated.getDob()
        );
    }
}
