package com.ceylon_fusion.Identity_Service.service;

import com.ceylon_fusion.Identity_Service.dto.request.ResetPasswordRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.request.UserLoginRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.request.UserRegistrationRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.request.UserUpdateRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserRegistrationResponseDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserResponseDTO;
import jakarta.validation.Valid;


public interface UserService {
    UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO requestDTO);
//    UserRegistrationResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO);
//    UserRegistrationResponseDTO getUserById(Long id);
//    void deleteUser(Long id);

   // UserRegistrationResponseDTO loginUser(UserLoginRequestDTO requestDTO);

    UserRegistrationResponseDTO loginUser(@Valid UserLoginRequestDTO requestDTO);
//

    UserResponseDTO getUserById(Long id);

//    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO);

    void deleteUser(Long id);

    String forgotPassword(String email);

    void resetPassword(String token, @Valid ResetPasswordRequestDTO requestDTO);

    UserResponseDTO updateUserProfile(Long userId, UserUpdateRequestDTO requestDTO);

    UserResponseDTO updateUserProfileByCfId(String cfId, UserUpdateRequestDTO requestDTO);
}
