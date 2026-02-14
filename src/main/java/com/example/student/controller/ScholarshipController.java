package com.example.student.controller;

import com.example.student.dto.ScholarshipDto;
import com.example.student.entity.Scholarship;
import com.example.student.service.ScholarshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/scholarship")
public class ScholarshipController {
    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService){
        this.scholarshipService = scholarshipService;
    }
    @GetMapping("/getall")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TEACHER')")
    ResponseEntity<List<ScholarshipDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "amount") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(scholarshipService.getAll(page,size,sortBy, order));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TEACHER')")
    ResponseEntity<Scholarship> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(scholarshipService.getById(id));
    }
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    ResponseEntity<Scholarship> createScholarship(@RequestBody Scholarship scholarship){
        return ResponseEntity.status(HttpStatus.CREATED).body(scholarshipService.create(scholarship));
    }
    @PostMapping("/bulk")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ScholarshipDto>> createScholarships(@RequestBody List<ScholarshipDto> scholarshipDtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scholarshipService.addScholarships(scholarshipDtos));
    }

    @PostMapping("/upload-csv")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> uploadCsv(@RequestParam("csv")MultipartFile file) throws IOException {
        scholarshipService.uploadCsvFile(file);
        return ResponseEntity.ok("csv file uploaded");
    }

}