package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Company;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.busbuddy.busbuddy.Dto.CompanyDto;

import java.util.Random;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepo companyRepo;

    // Generate a custom company ID in the format C001, C002, etc.
    public String generateCompanyId() {
        Random random = new Random();
        int id = 1000 + random.nextInt(9000);  // Random ID between 1000 and 9999
        return "C" + id;
    }

    // Example of creating a company with a generated companyId
    public String createCompany(CompanyDto companyDto) {
        String companyId = generateCompanyId();
        Company company = new Company(
                companyId,
                companyDto.getCompanyName(),
                companyDto.getCompanyAddress(),
                companyDto.getCompanyEmail(),
                companyDto.getCompanyPhone(),
                companyDto.getCompanyPassword()
        );
        companyRepo.save(company);
        return companyId;
    }
}
