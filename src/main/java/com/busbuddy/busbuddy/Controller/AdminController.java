package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Dto.AdminCreationDto;
import com.busbuddy.busbuddy.Dto.AdminLoginDto;
import com.busbuddy.busbuddy.Service.AdminService;
import com.busbuddy.busbuddy.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    // Add new admin
    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody AdminCreationDto newAdminDto) {
        if (adminService.adminExists(newAdminDto.getAdminName())) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }
        com.busbuddy.busbuddy.Model.Admin newAdmin = new com.busbuddy.busbuddy.Model.Admin();
        newAdmin.setAdminName(newAdminDto.getAdminName());
        newAdmin.setAdminPassword(newAdminDto.getAdminPassword());

        adminService.saveAdmin(newAdmin);

        return ResponseEntity.ok(Collections.singletonMap("id", newAdmin.getId()));
    }

    // Admin login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginDto loginDto) {
        boolean isValid = adminService.validateAdminCredentials(loginDto.getAdminName(), loginDto.getAdminPassword());

        if (isValid) {
            String token = jwtUtil.generateToken(loginDto.getAdminName());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}