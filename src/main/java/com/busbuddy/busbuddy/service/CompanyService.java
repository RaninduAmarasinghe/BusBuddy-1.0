package com.busbuddy.busbuddy.service;

import com.busbuddy.busbuddy.dto.CompanyDto;
import com.busbuddy.busbuddy.dto.CompanyLoginDto;
import com.busbuddy.busbuddy.model.Company;
import com.busbuddy.busbuddy.repository.CompanyRepo;
import com.busbuddy.busbuddy.util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CustomIdGenerator customIdGenerator;

    @Autowired
    private PasswordService passwordService;

    // Create a new company
    public String createCompany(CompanyDto companyDto) {
        String companyId = customIdGenerator.generateUniqueId("C", "company");

        String encodedPassword = passwordService.encode(companyDto.getCompanyPassword());

        Company company = new Company(
                companyId,
                companyDto.getCompanyName(),
                companyDto.getCompanyAddress(),
                companyDto.getCompanyEmail(),
                companyDto.getCompanyPhone(),
                encodedPassword
        );

        companyRepo.save(company);
        return companyId;
    }

    // Get company by ID
    public Optional<Company> getCompanyById(String companyId) {
        return companyRepo.findById(companyId);
    }

    // Get all companies
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    // Delete company by ID
    public void deleteCompany(String companyId) {
        companyRepo.deleteById(companyId);
    }

    // Update company by ID
    public String updateCompany(String companyId, CompanyDto updatedDto) {
        Optional<Company> companyOpt = companyRepo.findById(companyId);
        if (companyOpt.isEmpty()) {
            throw new RuntimeException("Company not found");
        }

        Company company = companyOpt.get();
        company.setCompanyName(updatedDto.getCompanyName());
        company.setCompanyAddress(updatedDto.getCompanyAddress());
        company.setCompanyEmail(updatedDto.getCompanyEmail());
        company.setCompanyPhone(updatedDto.getCompanyPhone());

        companyRepo.save(company);
        return "Company updated successfully";
    }

    // Login method
    public String login(CompanyLoginDto dto) {
        Company company = companyRepo.findByCompanyEmail(dto.getCompanyEmail())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (!passwordService.verify(dto.getCompanyPassword(), company.getCompanyPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return company.getCompanyId();  // Or return a JWT token here if needed
    }
    public Company findByCompanyEmail(String email) {
        return companyRepo.findByCompanyEmail(email).orElse(null);
    }
    public boolean verifyPassword(String raw, String encoded) {
        return passwordService.verify(raw, encoded);
    }
}