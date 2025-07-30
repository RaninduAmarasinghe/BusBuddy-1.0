package com.busbuddy.busbuddy.service;

import com.busbuddy.busbuddy.dto.AdminDto;
import com.busbuddy.busbuddy.model.Admin;
import com.busbuddy.busbuddy.repository.AdminRepo;
import com.busbuddy.busbuddy.util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private CustomIdGenerator customIdGenerator;



    public Admin findByAdminName(String adminName) {
        return adminRepo.findByAdminName(adminName);
    }

    public Admin saveAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setAdminName(adminDto.getAdminName());
        admin.setAdminPassword(passwordService.encode(adminDto.getAdminPassword()));
        return adminRepo.save(admin);
    }

    public boolean verifyPassword(String raw, String encoded) {
        return passwordService.verify(raw, encoded);
    }
}