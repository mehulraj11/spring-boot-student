package com.example.student.service.implementation;

import com.example.student.dto.ScholarshipDto;
import com.example.student.entity.Scholarship;
import com.example.student.repository.ScholarshipRepository;
import com.example.student.service.ScholarshipService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScholarshipServiceImpl implements ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    public ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository){
        this.scholarshipRepository = scholarshipRepository;
    }
    @Override
    public List<ScholarshipDto> getAll() {
        List<Scholarship> scholarships = scholarshipRepository.findAll();
        return scholarships.stream()
                .map(scholarship -> new ScholarshipDto(
                        scholarship.getTitle(),
                        scholarship.getEligibility(),
                        scholarship.getAmount(),
                        scholarship.isActive()
                ) ).toList();
    }

    @Override
    public Scholarship getById(Long id) {
        Scholarship scholarship = scholarshipRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found"));
        return scholarship;
    }

    @Override
    public Scholarship create(Scholarship scholarship) {
        Scholarship scholarship1 = new Scholarship();
        scholarship1.setTitle(scholarship.getTitle());
        scholarship1.setActive(scholarship.isActive());
        scholarship1.setAmount(scholarship.getAmount());
        scholarship1.setEligibility(scholarship.getEligibility());

        scholarshipRepository.save(scholarship1);
        return scholarship1;
    }

    @Override
    public Scholarship updateScholarship(Long id) {
        return null;
    }

    @Override
    public void deleteScholarShip(Long id) {
        Scholarship scholarship =scholarshipRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found"));
        scholarshipRepository.deleteById(id);
    }
}
