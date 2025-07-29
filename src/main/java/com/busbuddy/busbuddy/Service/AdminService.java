package com.busbuddy.busbuddy.Service;

import com.busbuddy.busbuddy.Model.Admin;
import com.busbuddy.busbuddy.Repository.AdminRepo;
import com.busbuddy.busbuddy.Util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomIdGenerator customIdGenerator;

    public boolean adminExists(String adminName) {
        return adminRepo.findByAdminName(adminName) != null;
    }

    public void saveAdmin(Admin admin) {
        // Generate unique ID with prefix, e.g. "ADM"
        String uniqueId = customIdGenerator.generateUniqueId("ADM", "admin");
        admin.setId(uniqueId);  // <-- Set unique ID here

        // Encode password
        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));

        // Save admin
        adminRepo.save(admin);
    }

    public boolean validateAdminCredentials(String adminName, String adminPassword) {
        Admin admin = adminRepo.findByAdminName(adminName);
        if (admin != null) {
            return passwordEncoder.matches(adminPassword, admin.getAdminPassword());
        }
        return false;
    }

    public Admin findByAdminName(String adminName) {
        return adminRepo.findByAdminName(adminName);
    }
}