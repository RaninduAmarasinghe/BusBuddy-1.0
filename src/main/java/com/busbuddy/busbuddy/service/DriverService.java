package com.busbuddy.busbuddy.service;

import com.busbuddy.busbuddy.dto.DriverLoginDto;
import com.busbuddy.busbuddy.model.Bus;
import com.busbuddy.busbuddy.model.Driver;
import com.busbuddy.busbuddy.repository.BusRepo;
import com.busbuddy.busbuddy.repository.CompanyRepo;
import com.busbuddy.busbuddy.repository.DriverRepo;
import com.busbuddy.busbuddy.dto.DriverDto;
import com.busbuddy.busbuddy.util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CustomIdGenerator customIdGenerator;

    @Autowired
    private PasswordService passwordService;

    public String createDriver(DriverDto driverDto) {
        // Generate a custom driver ID (this will be used as the MongoDB _id)
        String driverId = customIdGenerator.generateUniqueId("D", "driver");

        // Optionally fetch company name from company ID
        String companyName = companyRepo.findById(driverDto.getCompanyId())
                .map(company -> company.getCompanyName())
                .orElse("Unknown Company");

        // Validate that the bus exists
        Bus bus = busRepo.findById(driverDto.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Bus with ID " + driverDto.getBusId() + " does not exist"));

        // Create the Driver object
        Driver driver = new Driver();
        driver.setDriverId(driverId);  // This is now also the Mongo _id
        driver.setDriverName(driverDto.getDriverName());
        driver.setDriverEmail(driverDto.getDriverEmail());
        driver.setDriverPhone(driverDto.getDriverPhone());
        driver.setDriverPassword(passwordService.encode(driverDto.getDriverPassword())); // Secure hash
        driver.setCompanyId(driverDto.getCompanyId());
        driver.setCompanyName(companyName);
        driver.setBusId(driverDto.getBusId());

        // Save the driver and return the ID
        Driver savedDriver = driverRepo.save(driver);
        return savedDriver.getDriverId();
    }

    public Driver login(DriverLoginDto dto) {
        Driver driver = driverRepo.findByDriverEmail(dto.getDriverEmail())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (!passwordService.verify(dto.getDriverPassword(), driver.getDriverPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return driver; // You can generate JWT here if needed
    }

    public List<Driver> getDriverByCompany(String companyId) {
        return driverRepo.findByCompanyId(companyId);
    }

    public Driver getDriverByEmail(String email) {
        return driverRepo.findByDriverEmail(email).orElse(null);
    }

    public boolean verifyPassword(String raw, String encoded) {
        return passwordService.verify(raw, encoded);
    }
    public String encodePassword(String rawPassword) {
        return passwordService.encode(rawPassword);
    }
}