package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.DriverRepo;
import com.busbuddy.busbuddy.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.busbuddy.busbuddy.Dto.DriverDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/driver")
@CrossOrigin
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    DriverRepo driverRepo;

    @PostMapping("/add")
public ResponseEntity<String> createDriver(@RequestBody DriverDto driverDto) {
        try{
            String driverId = driverService.createDriver(driverDto);
            return ResponseEntity.ok("Driver created successfully: " + driverId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Driver>> getDriversByCompanyId(@PathVariable String companyId) {
        List<Driver> drivers = driverService.getDriverByCompany(companyId);
        return ResponseEntity.ok(drivers);
    }

    // Driver login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Driver driver) {
        Optional<Driver> driverOptional = driverRepo.findByDriverEmail(driver.getDriverEmail());

        if (driverOptional.isPresent()) {
            Driver dbDriver = driverOptional.get();

            if (dbDriver.getDriverPassword().equals(driver.getDriverPassword())) {
                // Return full driver data as structured JSON
                Map<String, Object> response = new HashMap<>();
                response.put("driverId", dbDriver.getDriverId());
                response.put("driverName", dbDriver.getDriverName());
                response.put("driverEmail", dbDriver.getDriverEmail());
                response.put("companyId", dbDriver.getCompanyId());
                response.put("companyName", dbDriver.getCompanyName());
                response.put("busId", dbDriver.getBusId());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Invalid email or password");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // Update driver
    @PutMapping("/update/{driverId}")
    public ResponseEntity<String> updateDriver(@PathVariable String driverId, @RequestBody Driver updatedDriver) {
        Optional<Driver> existingDriver = driverRepo.findById(driverId);
        if (existingDriver.isPresent()) {
            Driver driver = existingDriver.get();
            driver.setDriverName(updatedDriver.getDriverName());
            driver.setDriverEmail(updatedDriver.getDriverEmail());
            driver.setDriverPhone(updatedDriver.getDriverPhone());
            driver.setDriverPassword(updatedDriver.getDriverPassword());
            driverRepo.save(driver);
            return ResponseEntity.ok("Driver updated successfully");
        } else {
            return ResponseEntity.status(404).body("Driver not found");
        }
    }

    // Delete a driver
    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable String driverId) {
        if (driverRepo.existsById(driverId)) {
            driverRepo.deleteById(driverId);
            return ResponseEntity.ok("Driver deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Driver not found");
        }
    }

    @GetMapping("/search")
    public List<Driver> searchDrivers(@RequestParam String query) {
        return driverRepo.findByDriverIdContainingIgnoreCaseOrDriverNameContainingIgnoreCase(query, query);
    }
}