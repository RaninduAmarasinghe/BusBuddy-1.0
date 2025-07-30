package com.busbuddy.busbuddy.filter;

import com.busbuddy.busbuddy.model.Admin;
import com.busbuddy.busbuddy.service.AdminService;
import com.busbuddy.busbuddy.util.JwtUtil;
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
public class AdminJwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AdminService adminService;

    public AdminJwtAuthFilter(JwtUtil jwtUtil, @Lazy AdminService adminService) {
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
    }

    // Skip filtering for login and registration endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/admin/login") || path.equals("/admin/add") ||
                path.equals("/companies/add") || path.equals("/companies/login");
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
            try {
                adminName = jwtUtil.extractUsername(token); // use extractUsername() for consistency
            } catch (Exception e) {
                // Invalid token format or expired token - optionally log here
            }
        }

        if (adminName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Admin
            Admin admin = adminService.findByAdminName(adminName);
            if (admin != null && jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        adminName,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );
                authToken.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}