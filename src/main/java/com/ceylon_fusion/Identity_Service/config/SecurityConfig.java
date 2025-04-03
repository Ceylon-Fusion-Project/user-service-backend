package com.ceylon_fusion.Identity_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)


public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
                .cors(cors -> cors.disable()) // Disable CORS if not needed or configure it explicitly
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                   //      Public Endpoints
//                        .requestMatchers("/api/v1/users/register", "/api/v1/users/login", "/api/v1/users/forgot-password", "/api/v1/users/reset-password").permitAll()
//
//                        // Admin Endpoints
//                        .requestMatchers(
//                                "/api/v1/users/get-by-id{id}",
//                                "/api/v1/users/update-by-id{id}",
//                                "/api/v1/users/delete-by-id{id}",
//                                "/api/v1/users/bookingHistory",
//                                "/api/v1/users/purchaseHistory"
//                        ).hasAuthority("ADMIN")
//
//                        // Seller Endpoints
//                        .requestMatchers("/api/v1/users/purchaseHistory").hasAuthority("SELLER")
//
//                        // Tourist Endpoints
//                        .requestMatchers(
//                                "/api/v1/users/{userId}/purchase-history",
//                                "/api/v1/users/{userId}/booking-history"
//                        ).hasAuthority("TOURIST")
//
//                        // Any other requests require authentication
//                        .anyRequest().authenticated()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/api/v1/users/**").permitAll() // Public Endpoints
                        .anyRequest().authenticated());


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
