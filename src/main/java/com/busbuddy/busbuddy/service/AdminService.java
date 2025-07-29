package com.busbuddy.busbuddy.service;

import com.busbuddy.busbuddy.model.Admin;
import com.busbuddy.busbuddy.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin findByAdminName(String adminName) {
        return adminRepo.findByAdminName(adminName);
    }

    public Admin saveAdmin(Admin admin) {
        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
        return adminRepo.save(admin);
    }

    public boolean verifyPassword(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }
}