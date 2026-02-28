package com.example.student.service.implementation;

import com.example.student.dto.AddStudentDto;
import com.example.student.dto.StudentDto;
import com.example.student.entity.Student;
import com.example.student.entity.Users;
import com.example.student.exception.ContextAuthentication;
import com.example.student.exception.CreateStudentException;
import com.example.student.exception.StudentException;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.UserRepository;
import com.example.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }
    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "UNKNOWN";
    }

    @Cacheable(value = "studentsList", key = "'all'")
    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        log.info("{} has retrieved all students", getCurrentUserEmail());
        return students
                .stream()
                .map(student -> new StudentDto(
                        student.getName(),
                        student.getDob()))
                .toList();
    }
    @Cacheable(value = "students", key = "#id")
    @Override
    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        log.info("{} has retrieved student data by using id{}",getCurrentUserEmail(), student.getStudentId());
        return new StudentDto(
                student.getName(),
                student.getDob());
    }

    @Override
    public StudentDto createNewStudent(AddStudentDto dto) {

        Users user = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new ContextAuthentication("User not found"));

        if (studentRepository.existsByUsers_UserId(user.getUserId())) {
            throw new CreateStudentException("Student profile already exists");
        }

        Student student = new Student();
        student.setUsers(user);
        student.setName(dto.getName());
        student.setDob(dto.getDob());

        Student savedStudent = studentRepository.save(student);

        log.info("Student profile created for email: {}", getCurrentUserEmail());

        return new StudentDto(
                savedStudent.getName(),
                savedStudent.getDob()
        );
    }
    @CacheEvict(value = "students", key = "#id")
    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        studentRepository.delete(student);
        log.info("student id :'{}' has been deleted", student.getStudentId());
    }

    @CachePut(value = "students", key = "#id")
    @Override
    public StudentDto updateStudent(Long id, AddStudentDto addStudentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(id));
        student.setName(addStudentDto.getName());
        student.setDob(addStudentDto.getDob());
        Student updatedStudent = studentRepository.save(student);
        log.info("student id :{} profile updated", student.getStudentId());
        return new StudentDto(
                updatedStudent.getName(),
                updatedStudent.getDob()
        );
    }
    @CachePut(value = "students", key = "#id")
    @Override
    public StudentDto patchStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException(id));
        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> student.setName((String) value);
                case "dob" -> student.setDob(LocalDate.parse((String) value));
            }
        });
        Student updated = studentRepository.save(student);
        log.info("student id :{} has been modified", student.getStudentId());
        return new StudentDto(
                updated.getName(),
                updated.getDob()
        );
    }
}