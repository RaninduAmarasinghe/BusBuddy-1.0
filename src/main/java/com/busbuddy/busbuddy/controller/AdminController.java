package com.busbuddy.busbuddy.controller;

import com.busbuddy.busbuddy.dto.AdminDto;
import com.busbuddy.busbuddy.model.Admin;
import com.busbuddy.busbuddy.service.AdminService;
import com.busbuddy.busbuddy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<?> register(@RequestBody AdminDto adminDto) {
        if (adminService.findByAdminName(adminDto.getAdminName()) != null) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }
        adminService.saveAdmin(adminDto);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminDto loginDto) {
        Admin admin = adminService.findByAdminName(loginDto.getAdminName());
        if (admin != null && adminService.verifyPassword(loginDto.getAdminPassword(), admin.getAdminPassword())) {
            String token = jwtUtil.generateToken(admin.getAdminName());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}