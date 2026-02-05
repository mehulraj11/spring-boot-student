package com.example.student.controller;

import com.example.student.dto.ScholarshipDto;
import com.example.student.entity.Scholarship;
import com.example.student.service.ScholarshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scholarship")
public class ScholarshipController {
    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService){
        this.scholarshipService = scholarshipService;
    }
    @GetMapping("/getall")
    ResponseEntity<List<ScholarshipDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(scholarshipService.getAll());
    }

    @GetMapping("/get/{}")
    ResponseEntity<Scholarship> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(scholarshipService.getById(id));
    }
    @PostMapping("/create")
    ResponseEntity<Scholarship> createScholarship(@RequestBody Scholarship scholarship){
        return ResponseEntity.status(HttpStatus.CREATED).body(scholarshipService.create(scholarship));
    }
}
