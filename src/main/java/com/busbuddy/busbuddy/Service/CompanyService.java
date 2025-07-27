package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Company;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import com.busbuddy.busbuddy.Util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.busbuddy.busbuddy.Dto.CompanyDto;

import java.util.Random;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomIdGenerator customIdGenerator;

    // Generate a custom company ID in the format C001, C002, etc.

    public String createCompany(CompanyDto companyDto) {
        String companyId = customIdGenerator.generateUniqueId("C", "company");
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
