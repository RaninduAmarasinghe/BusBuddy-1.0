package com.busbuddy.busbuddy.repository;

import com.busbuddy.busbuddy.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepo extends MongoRepository<Driver, String> {
    Optional<Driver> findByDriverEmail(String driverEmail);
    List<Driver> findByCompanyId(String companyId);
    List<Driver> findByDriverIdContainingIgnoreCaseOrDriverNameContainingIgnoreCase(String driverId, String driverName);
}
