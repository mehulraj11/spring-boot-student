package com.example.student.service.implementation;

import com.example.student.dto.ScholarshipDto;
import com.example.student.entity.Scholarship;
import com.example.student.repository.ScholarshipRepository;
import com.example.student.service.ScholarshipService;
import com.example.student.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Slf4j
@Service
public class ScholarshipServiceImpl implements ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final StorageService storageService;

    public ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository, StorageService storageService){
        this.scholarshipRepository = scholarshipRepository;
        this.storageService = storageService;
    }
    @Override
    public Page<ScholarshipDto> getAll(
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

        return scholarships.map(scholarship ->
                new ScholarshipDto(
                        scholarship.getTitle(),
                        scholarship.getEligibility(),
                        scholarship.getAmount(),
                        scholarship.isActive()
                )
        );
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
    @Override
    public List<ScholarshipDto> addScholarships(List<ScholarshipDto> scholarshipDto) {
        List<Scholarship> scholarships = scholarshipDto.stream()
                .map(dto -> new Scholarship(
                        null,
                        dto.getTitle(),
                        dto.getEligibility(),
                        dto.getAmount(),
                        dto.isActive()
                ))
                .toList();

        List<Scholarship> saved = scholarshipRepository.saveAll(scholarships);
        return saved.stream()
                .map(s -> new ScholarshipDto(
                        s.getTitle(),
                        s.getEligibility(),
                        s.getAmount(),
                        s.isActive()
                ))
                .toList();
    }

    @Override
    public void uploadCsvFile(MultipartFile file) throws IOException {
        List<String> rows = storageService.uploadCsv(file);
        List<Scholarship> scholarships = rows.stream()
                .map(row -> {
                    String[] data = row.split(","); // data will be in a foramt of noraml string with ,
                    Scholarship s = new Scholarship();
                    s.setTitle(data[0]);
                    s.setEligibility(data[1]);
                    s.setAmount(Double.parseDouble(data[2]));
                    s.setActive(Boolean.parseBoolean(data[3]));
                    return s;
                })
                .toList();
        scholarshipRepository.saveAll(scholarships);
    }
}
