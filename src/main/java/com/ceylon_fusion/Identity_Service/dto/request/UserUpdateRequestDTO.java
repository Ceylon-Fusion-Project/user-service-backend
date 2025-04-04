package com.ceylon_fusion.Identity_Service.dto.request;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserUpdateRequestDTO {
    private String username;
    private String email;
    private String Role;


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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setProfilePhoto(MultipartFile profilePhoto) {
    }

    public String getAddress() {
        return "";
    }



    public String getCountry() {
        return "";
    }

    public MultipartFile getProfilePhoto() {
        return null;
    }

    public String getPhoneNumber() {
        return "";
    }
}
