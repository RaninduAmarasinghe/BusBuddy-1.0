package com.busbuddy.busbuddy.service;

import com.busbuddy.busbuddy.model.Company;
import com.busbuddy.busbuddy.repository.CompanyRepo;
import com.busbuddy.busbuddy.util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.busbuddy.busbuddy.dto.CompanyDto;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomIdGenerator customIdGenerator;


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
