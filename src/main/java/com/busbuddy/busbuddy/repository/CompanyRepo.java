package com.busbuddy.busbuddy.repository;

import com.busbuddy.busbuddy.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends MongoRepository<Company, String> {
    Optional<Company> findByCompanyEmail(String companyEmail);
}