package com.ceylon_fusion.Identity_Service.service.serviceIMPL;

import com.ceylon_fusion.Identity_Service.dto.request.ResetPasswordRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.request.UserLoginRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.request.UserRegistrationRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.request.UserUpdateRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserRegistrationResponseDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserResponseDTO;
import com.ceylon_fusion.Identity_Service.entity.User;
import com.ceylon_fusion.Identity_Service.exception.EmailAlreadyExistsException;
import com.ceylon_fusion.Identity_Service.exception.ResourceNotFoundException;
import com.ceylon_fusion.Identity_Service.exception.UsernameAlreadyExistsException;
import com.ceylon_fusion.Identity_Service.repo.UserRepo;
import com.ceylon_fusion.Identity_Service.service.UserService;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class UserServiceIMPL implements UserService {

    @Autowired
    private final UserRepo userRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public UserServiceIMPL(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    private static final Logger logger = LoggerFactory.getLogger(UserServiceIMPL.class);


    @Override
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO requestDTO) {
        try {
            if (requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email cannot be null or empty");
            }
            // Existing validation checks
            if (userRepo.existsByUsername(requestDTO.getUsername())) {
                throw new UsernameAlreadyExistsException("Username already taken");
            }
            if (userRepo.existsByEmail(requestDTO.getEmail())) {
                throw new EmailAlreadyExistsException("Email already registered");
            }
            if (userRepo.existsByPhoneNumber(requestDTO.getPhoneNumber())) {
                throw new IllegalArgumentException("Phone number already in use");
            }

            // Create and populate user
            User user = new User();
            user.setUsername(requestDTO.getUsername());
            user.setEmail(requestDTO.getEmail());
            user.setPhoneNumber(requestDTO.getPhoneNumber());
            user.setCfId(requestDTO.getCfId());
            user.setCountry(requestDTO.getCountry());
            user.setAddress(requestDTO.getAddress());
            user.setCurrency(requestDTO.getCurrency());
            user.setCity(requestDTO.getCity());
            user.setState(requestDTO.getState());
            user.setZipCode(requestDTO.getZipCode());
            user.setRole(requestDTO.getRole());

            // Set default empty password (since auth is handled by Keycloak)
            user.setPassword("");

            // Set timestamps
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            // Save user
            User savedUser = userRepo.save(user);

            return new UserRegistrationResponseDTO(
                    savedUser.getUserId(),
                    savedUser.getUsername(),
                    savedUser.getEmail(),
                    savedUser.getRole(),
                    savedUser.getCreatedAt(),
                    savedUser.getUpdatedAt()
            );
        } catch (Exception e) {
            // Add proper logging
            logger.error("Registration failed: {}", e.getMessage());
            throw e; // Re-throw to be handled by controller
        }
    }
    @Override
    public UserRegistrationResponseDTO loginUser(UserLoginRequestDTO requestDTO) {
        // Validate email and password
        User user = userRepo.findByEmail(requestDTO.getEmail()) // FIXED: Use findByEmail instead of findByUsername
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));



        // Return user details (include role)
        return new UserRegistrationResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(), // FIXED: Added missing role
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    @Override
    public String forgotPassword(String username) {
        // Check if the username exists
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No user found with the provided username."));

        // Generate a reset token
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setTokenExpiry(LocalDateTime.now().plusHours(1)); // Token expires in 1 hour
        userRepo.save(user);

        // Return the reset token to the client
        return resetToken;
    }

    @Override
    public void resetPassword(String token, ResetPasswordRequestDTO requestDTO) {
        // Validate the token and find the user
        User user = userRepo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired password reset token."));

        if (user.getTokenExpiry().isBefore(ChronoLocalDateTime.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))) {
            throw new RuntimeException("Password reset token has expired.");
        }

        // Update the user's password

        user.setResetToken(null); // Clear the reset token
        user.setTokenExpiry(null); // Clear the expiry
        userRepo.save(user);
    }

    @Override
    public UserResponseDTO updateUserProfile(Long userId, UserUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public UserResponseDTO updateUserProfileByCfId(String cfId, UserUpdateRequestDTO requestDTO) {
        return null;
    }

    @Override
    public UserResponseDTO getUserByCfId(String cfId) {
        User user = userRepo.findByCfId(cfId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with cfId: " + cfId));

        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),

                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }



    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Delete the user
        userRepo.delete(user);
    }


    private String storeProfilePhoto(MultipartFile file, String cfId) {
        try {
            // Generate unique filename with CF_ID and timestamp
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = "profile-" + cfId + "-" + System.currentTimeMillis() + fileExtension;

            // Ensure upload directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store profile photo: " + ex.getMessage());
        }
    }

//    private UserResponseDTO convertToResponseDTO(User user) {
//        UserResponseDTO responseDTO = new UserResponseDTO(
//                user.getUserId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getCreatedAt(),
//                user.getUpdatedAt()
//        );
//
//        // Set additional fields
//        responseDTO.setCfId(user.getCf_id());
//        responseDTO.setAddress(user.getAddress());
//        responseDTO.setPhoneNumber(String.valueOf(user.getPhone_number()));
//        responseDTO.setCountry(user.getCountry());
//        responseDTO.setRole(user.getRole().name());
//        responseDTO.setProfilePhotoUrl(user.getProfilePhotoPath() != null ?
//                "/api/v1/users/profile-photo/" + user.getUserId() : null);
//
//        return responseDTO;
//    }

//    private UserResponseDTO updateProfile(User user, UserUpdateRequestDTO requestDTO) {
//        // Update basic fields
//        if (requestDTO.getUsername() != null) {
//            user.setUsername(requestDTO.getUsername());
//        }
//        if (requestDTO.getEmail() != null) {
//            user.setEmail(requestDTO.getEmail());
//        }
//        if (requestDTO.getCountry() != null) {
//            user.setCountry(requestDTO.getCountry());
//        }
//        if (requestDTO.getRole() != null) {
//            user.setRole(Role.valueOf(requestDTO.getRole().toUpperCase()));
//        }
//        if (requestDTO.getPhoneNumber() != null) {
//            try {
//                // Remove all non-digit characters and parse to long
//                String numericPhone = requestDTO.getPhoneNumber().replaceAll("[^0-9]", "");
//                user.setPhone_number(String.valueOf(numericPhone));
//            } catch (NumberFormatException e) {
//                throw new IllegalArgumentException("Invalid phone number format");
//            }
//        }
//
//        // Handle profile photo
//        if (requestDTO.getProfilePhoto() != null && !requestDTO.getProfilePhoto().isEmpty()) {
//            String fileName = storeProfilePhoto(requestDTO.getProfilePhoto(), user.getCf_id());
//            user.setProfilePhotoPath(fileName);
//        }
//
//        user.setUpdatedAt(LocalDateTime.now());
//        User updatedUser = userRepo.save(user);
//        return convertToResponseDTO(updatedUser);
//    }

}