package com.example.student.controller;

import com.example.student.dto.AddStudentDto;
import com.example.student.dto.StudentDto;
import com.example.student.service.StudentServie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
//@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class    StudentController {
    private final StudentServie studentServie;

    public StudentController(StudentServie studentServie) {
        this.studentServie = studentServie;
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentServie.getAllStudents());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentServie.getStudent(id));
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDto> createStudent(@RequestBody AddStudentDto addStudentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentServie.createNewStudent(addStudentDto));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentServie.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody AddStudentDto addStudentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(studentServie.updateStudent(id, addStudentDto));
    }

    @PatchMapping("/students/{id}")
    public ResponseEntity<StudentDto> patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(studentServie.patchStudent(id, updates));
    }
}
