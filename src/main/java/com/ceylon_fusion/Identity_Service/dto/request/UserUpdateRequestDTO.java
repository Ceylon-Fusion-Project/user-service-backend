package com.ceylon_fusion.Identity_Service.dto.request;

import com.ceylon_fusion.Identity_Service.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateRequestDTO {
    private String username;
    private String email;
    private Role role;
}
