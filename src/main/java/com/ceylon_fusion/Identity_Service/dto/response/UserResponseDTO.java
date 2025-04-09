package com.ceylon_fusion.Identity_Service.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private Long userId;
    private String cfId;// Updated from 'id' to match User entity
    private String username;
    private String email;

    public String getCfId() {
        return cfId;
    }


    @Setter
    private String profilePhotoPath;

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

    public void setCfId(String cfId) {
    }

    public void setAddress(String address) {
    }

    public void setPhoneNumber(String s) {
    }

    public void setCountry(String country) {
    }

    public void setRole(String name) {
    }

    public void setProfilePhotoUrl(String s) {

    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

}
