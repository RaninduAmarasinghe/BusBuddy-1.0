package com.busbuddy.busbuddy.controller;

import com.busbuddy.busbuddy.dto.CompanyDto;
import com.busbuddy.busbuddy.model.Company;
import com.busbuddy.busbuddy.repository.CompanyRepo;
import com.busbuddy.busbuddy.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepo companyRepo;

    // Add a new company
    @PostMapping("/add")
    public ResponseEntity<String> createCompany(@RequestBody CompanyDto companyDto) {
        String companyId = companyService.createCompany(companyDto);
        return ResponseEntity.ok("Company created successfully with ID: " + companyId);
    }

    // Get company by ID
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable String companyId) {
        Optional<Company> company = companyRepo.findById(companyId);
        return company.map(this::mapToDto).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all companies
    @GetMapping("/all")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> dtoList = companyRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Delete company by ID
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable String companyId) {
        companyRepo.deleteById(companyId);
        return ResponseEntity.ok("Company deleted successfully");
    }

    // Update company by ID
    @PutMapping("/update/{companyId}")
    public ResponseEntity<String> updateCompany(@PathVariable String companyId, @RequestBody CompanyDto updatedDto) {
        Optional<Company> existingCompany = companyRepo.findById(companyId);
        if (existingCompany.isPresent()) {
            Company company = existingCompany.get();
            company.setCompanyName(updatedDto.getCompanyName());
            company.setCompanyAddress(updatedDto.getCompanyAddress());
            company.setCompanyEmail(updatedDto.getCompanyEmail());
            company.setCompanyPhone(updatedDto.getCompanyPhone());
            company.setCompanyPassword(updatedDto.getCompanyPassword());
            companyRepo.save(company);
            return ResponseEntity.ok("Company updated successfully");
        } else {
            return ResponseEntity.status(404).body("Company not found");
        }
    }

    // Company login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CompanyDto companyDto) {
        // NOTE: Make sure companyEmail is UNIQUE in DB
        Optional<Company> optionalCompany = companyRepo.findByCompanyEmail(companyDto.getCompanyEmail());

        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            if (company.getCompanyPassword().equals(companyDto.getCompanyPassword())) {
                return ResponseEntity.ok(company.getCompanyId());
            } else {
                return ResponseEntity.status(401).body("Incorrect password");
            }
        } else {
            return ResponseEntity.status(404).body("Company not found");
        }
    }

    // Utility: Map Model to DTO
    private CompanyDto mapToDto(Company company) {
        CompanyDto dto = new CompanyDto();
        dto.setCompanyId(company.getCompanyId());
        dto.setCompanyName(company.getCompanyName());
        dto.setCompanyAddress(company.getCompanyAddress());
        dto.setCompanyEmail(company.getCompanyEmail());
        dto.setCompanyPhone(company.getCompanyPhone());
        dto.setCompanyPassword(company.getCompanyPassword());
        return dto;
    }
}