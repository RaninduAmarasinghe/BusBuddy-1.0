package com.busbuddy.busbuddy.config;

import com.busbuddy.busbuddy.filter.AdminJwtAuthFilter;
import com.busbuddy.busbuddy.filter.CompanyJwtAuthFilter;
import com.busbuddy.busbuddy.filter.DriverJwtAuthFilter;
import com.busbuddy.busbuddy.service.AdminService;
import com.busbuddy.busbuddy.service.CompanyService;
import com.busbuddy.busbuddy.service.DriverService;
import com.busbuddy.busbuddy.util.JwtUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final AdminService adminService;
    private final CompanyService companyService;
    private final DriverService driverService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(
            AdminService adminService,
            CompanyService companyService,
            DriverService driverService,
            JwtUtil jwtUtil
    ) {
        this.adminService = adminService;
        this.companyService = companyService;
        this.driverService = driverService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AdminJwtAuthFilter adminJwtAuthFilter() {
        return new AdminJwtAuthFilter(jwtUtil, adminService);
    }

    @Bean
    public CompanyJwtAuthFilter companyJwtAuthFilter() {
        return new CompanyJwtAuthFilter(jwtUtil, companyService);
    }

    @Bean
    public DriverJwtAuthFilter driverJwtAuthFilter() {
        return new DriverJwtAuthFilter(jwtUtil, driverService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Allow browser preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public endpoints
                        .requestMatchers(
                                "/admin/login",
                                "/admin/add",

                                "/companies/login",
                                "/companies/add",

                                "/driver/login",
                                "/driver/add",

                                "/bus/add",
                                "/bus/company/**",
                                "/bus/details/**",
                                "/bus/schedules",

                                "/ws-location/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(
                adminJwtAuthFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        http.addFilterBefore(
                companyJwtAuthFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        http.addFilterBefore(
                driverJwtAuthFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of("*"));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}