package com.busbuddy.busbuddy.controller;

import com.busbuddy.busbuddy.dto.CompanyDto;
import com.busbuddy.busbuddy.dto.CompanyLoginDto;
import com.busbuddy.busbuddy.model.Company;
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

    // Add a new company
    @PostMapping("/add")
    public ResponseEntity<String> createCompany(@RequestBody CompanyDto companyDto) {
        String companyId = companyService.createCompany(companyDto);
        return ResponseEntity.ok("Company created successfully with ID: " + companyId);
    }

    // Get company by ID
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable String companyId) {
        Optional<Company> companyOpt = companyService.getCompanyById(companyId);
        return companyOpt.map(this::mapToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all companies
    @GetMapping("/all")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> dtoList = companyService.getAllCompanies()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Delete company by ID
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable String companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok("Company deleted successfully");
    }

    // Update company by ID
    @PutMapping("/update/{companyId}")
    public ResponseEntity<String> updateCompany(@PathVariable String companyId,
                                                @RequestBody CompanyDto updatedDto) {
        try {
            String result = companyService.updateCompany(companyId, updatedDto);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Company login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CompanyLoginDto companyLoginDto) {
        try {
            String companyId = companyService.login(companyLoginDto);
            return ResponseEntity.ok(companyId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
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