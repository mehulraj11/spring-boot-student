package com.example.student.service.implementation;

import com.example.student.dto.ScholarshipDto;
import com.example.student.entity.Scholarship;
import com.example.student.repository.ScholarshipRepository;
import com.example.student.service.ScholarshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ScholarshipServiceImpl implements ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    public ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository){
        this.scholarshipRepository = scholarshipRepository;
    }
    @Override
    public List<ScholarshipDto> getAll(
            int page,
            int size,
            String sortBy,
            String order
    ) {
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Scholarship> scholarships = scholarshipRepository.findAll(pageable);
        log.info("list of all scholarships has been retrieved");

        return scholarships.stream()
                .map(scholarship -> new ScholarshipDto(
                        scholarship.getTitle(),
                        scholarship.getEligibility(),
                        scholarship.getAmount(),
                        scholarship.isActive()
                )).toList();
    }

    @Override
    public Scholarship getById(Long id) {
        Scholarship scholarship = scholarshipRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found"));
        log.info("ID: {} -> scholarship has been retrieved", scholarship.getScholarshipId());
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
        log.info("{} has been created(scholarship)",scholarship1.getScholarshipId());
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
        log.info("{} has been deleted(scholarship)", scholarship.getScholarshipId());
        scholarshipRepository.deleteById(id);
    }
}
