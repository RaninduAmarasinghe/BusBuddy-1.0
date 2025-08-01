package com.busbuddy.busbuddy.controller;

import com.busbuddy.busbuddy.dto.DriverDto;
import com.busbuddy.busbuddy.dto.DriverLoginDto;
import com.busbuddy.busbuddy.model.Driver;
import com.busbuddy.busbuddy.repository.DriverRepo;
import com.busbuddy.busbuddy.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/driver")
@CrossOrigin
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepo driverRepo;

    // Create (Register) Driver
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> createDriver(
            @RequestBody DriverDto driverDto,
            @RequestParam(required = false) String companyId) {

        if (companyId != null && (driverDto.getCompanyId() == null || driverDto.getCompanyId().isEmpty())) {
            driverDto.setCompanyId(companyId);
        }

        try {
            String driverId = driverService.createDriver(driverDto);
            Map<String, String> response = new HashMap<>();
            response.put("driverId", driverId);
            response.put("message", "Driver created successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DriverLoginDto driverLoginDto) {
        Optional<Driver> driverOptional = driverRepo.findByDriverEmail(driverLoginDto.getDriverEmail());

        if (driverOptional.isPresent()) {
            Driver dbDriver = driverOptional.get();
            if (driverService.verifyPassword(driverLoginDto.getDriverPassword(), dbDriver.getDriverPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("driverId", dbDriver.getDriverId());
                response.put("driverName", dbDriver.getDriverName());
                response.put("driverEmail", dbDriver.getDriverEmail());
                response.put("companyId", dbDriver.getCompanyId());
                response.put("companyName", dbDriver.getCompanyName());
                response.put("busId", dbDriver.getBusId());

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
    }

    // Update driver
    @PutMapping("/update/{driverId}")
    public ResponseEntity<String> updateDriver(@PathVariable String driverId, @RequestBody Driver updatedDriver) {
        Optional<Driver> existingDriverOpt = driverRepo.findById(driverId);
        if (existingDriverOpt.isPresent()) {
            Driver driver = existingDriverOpt.get();
            driver.setDriverName(updatedDriver.getDriverName());
            driver.setDriverEmail(updatedDriver.getDriverEmail());
            driver.setDriverPhone(updatedDriver.getDriverPhone());

            // Encode the new password (if changed)
            if (!driverService.verifyPassword(updatedDriver.getDriverPassword(), driver.getDriverPassword())) {
                driver.setDriverPassword(driverService.encodePassword(updatedDriver.getDriverPassword()));
            }

            driverRepo.save(driver);
            return ResponseEntity.ok("Driver updated successfully");
        } else {
            return ResponseEntity.status(404).body("Driver not found");
        }
    }

    // Delete driver
    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable String driverId) {
        if (driverRepo.existsById(driverId)) {
            driverRepo.deleteById(driverId);
            return ResponseEntity.ok("Driver deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Driver not found");
        }
    }

    // Search by ID or name
    @GetMapping("/search")
    public ResponseEntity<List<Driver>> searchDrivers(@RequestParam String query) {
        List<Driver> results = driverRepo.findByDriverIdContainingIgnoreCaseOrDriverNameContainingIgnoreCase(query, query);
        return ResponseEntity.ok(results);
    }
}