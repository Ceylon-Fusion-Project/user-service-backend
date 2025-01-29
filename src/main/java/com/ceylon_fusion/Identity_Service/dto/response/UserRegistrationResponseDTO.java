package com.ceylon_fusion.Identity_Service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor

@Data
public class UserRegistrationResponseDTO {
    private Long userId;
    private String username;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor that initializes all fields
    public UserRegistrationResponseDTO(Long userId, String username, String email,  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Optional: Update the toString() method to include all fields
    @Override
    public String toString() {
        return "UserRegistrationResponseDTO{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +

                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
