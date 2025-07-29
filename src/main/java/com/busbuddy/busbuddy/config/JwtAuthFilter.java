package com.busbuddy.busbuddy.config;

import com.busbuddy.busbuddy.Model.Admin;
import com.busbuddy.busbuddy.Service.AdminService;
import com.busbuddy.busbuddy.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AdminService adminService;

    public JwtAuthFilter(JwtUtil jwtUtil, @Lazy AdminService adminService) {
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/admin/login") || path.equals("/admin/add");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String adminName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            adminName = jwtUtil.extractAdminName(token);
        }

        if (adminName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Admin admin = adminService.findByAdminName(adminName);
            if (admin != null && jwtUtil.validateToken(token)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        adminName,
                        null,
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}