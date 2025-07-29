package com.busbuddy.busbuddy.service;

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

    public String createDriver(DriverDto dto) {
        // Generate driver ID
        String driverId = customIdGenerator.generateUniqueId("D", "driver");

        // Fetch company name (optional, not used in saving driver)
        String companyName = companyRepo.findById(dto.getCompanyId())
                .map(company -> company.getCompanyName())
                .orElse("Unknown Company");

        // Validate bus exists
        Bus bus = busRepo.findById(dto.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Bus with ID " + dto.getBusId() + " does not exist"));

        // Create and populate driver
        Driver driver = new Driver();
        driver.setDriverId(driverId);
        driver.setDriverName(dto.getDriverName()); // Assuming you have getName() in DTO
        driver.setDriverEmail(dto.getDriverEmail());
      //  driver.setDriverNic(dto.getNic()); // Set NIC
        driver.setDriverPhone(dto.getDriverPhone());
        driver.setDriverPassword(dto.getDriverPassword());
        driver.setCompanyId(dto.getCompanyId());
        driver.setCompanyName(companyName);
        driver.setBusId(dto.getBusId());


        // Save and return driver ID
        Driver savedDriver = driverRepo.save(driver);
        return savedDriver.getDriverId();
    }


    public List<Driver> getDriverByCompany(String companyId) {
        return driverRepo.findByCompanyId(companyId);
    }

    public Driver getDriverByEmail(String email) {
        return driverRepo.findByDriverEmail(email).orElse(null);
    }
}