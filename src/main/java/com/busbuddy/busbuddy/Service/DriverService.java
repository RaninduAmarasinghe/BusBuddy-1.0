package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Bus;
import com.busbuddy.busbuddy.Model.Driver;
import com.busbuddy.busbuddy.Repository.BusRepo;
import com.busbuddy.busbuddy.Repository.CompanyRepo;
import com.busbuddy.busbuddy.Repository.DriverRepo;
import com.busbuddy.busbuddy.Dto.DriverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private CompanyRepo companyRepo;

    // Generate a custom ID in the range 1000 - 9999
    private String generateUniqueDriverId() {
        String id;
        do {
            id = String.valueOf(1000 + new Random().nextInt(9000));
        } while (driverRepo.existsById(id));
        return id;
    }

    public String createDriver(DriverDto dto) {
        String driverId = generateUniqueDriverId();

        // Fetch company name
        String companyName = companyRepo.findById(dto.getCompanyId())
                .map(company -> company.getCompanyName())
                .orElse("Unknown Company");

        // Validate bus exists
        Bus bus = busRepo.findById(dto.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Bus with ID " + dto.getBusId() + " does not exist"));

        Driver driver = new Driver(
                driverId,
                dto.getDriverName(),
                dto.getDriverEmail(),
                dto.getDriverPhone(),
                dto.getDriverPassword(),
                dto.getCompanyId(),
                companyName,
                dto.getBusId()
        );

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