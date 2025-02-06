package com.ceylon_fusion.Identity_Service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
