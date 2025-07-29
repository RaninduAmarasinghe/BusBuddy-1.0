package com.busbuddy.busbuddy.repository;

import com.busbuddy.busbuddy.model.AlertMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepo extends MongoRepository<AlertMessage, String> {
    List<AlertMessage> findByCompanyIdOrderByIdDesc(String companyId);
}