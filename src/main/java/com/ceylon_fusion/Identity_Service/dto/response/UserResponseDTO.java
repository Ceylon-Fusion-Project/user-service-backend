package com.ceylon_fusion.Identity_Service.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private Long userId; // Updated from 'id' to match User entity
    private String username;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = id;
        this.username = username;
        this.email = email;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
