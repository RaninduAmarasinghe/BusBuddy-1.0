package com.busbuddy.busbuddy.repository;

import com.busbuddy.busbuddy.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Admin findByAdminName(String adminName);
}
