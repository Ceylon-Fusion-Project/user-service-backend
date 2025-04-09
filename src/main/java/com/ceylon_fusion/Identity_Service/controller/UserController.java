package com.ceylon_fusion.Identity_Service.controller;

import com.ceylon_fusion.Identity_Service.dto.request.*;
import com.ceylon_fusion.Identity_Service.dto.response.*;
import com.ceylon_fusion.Identity_Service.entity.User;
import com.ceylon_fusion.Identity_Service.exception.EmailAlreadyExistsException;
import com.ceylon_fusion.Identity_Service.exception.ResourceNotFoundException;
import com.ceylon_fusion.Identity_Service.exception.UsernameAlreadyExistsException;
import com.ceylon_fusion.Identity_Service.service.UserBookingHistoryService;
import com.ceylon_fusion.Identity_Service.service.UserPurchaseHistoryService;
import com.ceylon_fusion.Identity_Service.service.UserService;
import com.ceylon_fusion.Identity_Service.util.StandardResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UserPurchaseHistoryService userPurchaseHistoryService;

    @Autowired
    private UserBookingHistoryService userBookingHistoryService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/register")
    public ResponseEntity<StandardResponse> registerUser(
            @RequestBody @Valid UserRegistrationRequestDTO requestDTO) {
        try {
            logger.info("Attempting to register user: {}", requestDTO.getUsername());
            UserRegistrationResponseDTO response = userService.registerUser(requestDTO);
            logger.info("User registered successfully: {}", requestDTO.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new StandardResponse(201, "User registered successfully", response));
        } catch (UsernameAlreadyExistsException e) {
            logger.warn("Registration failed - username exists: {}", requestDTO.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new StandardResponse(409, e.getMessage(), null));
        } catch (EmailAlreadyExistsException e) {
            logger.warn("Registration failed - email exists: {}", requestDTO.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new StandardResponse(409, e.getMessage(), null));
        } catch (Exception e) {
            logger.error("Registration failed for user {}: {}", requestDTO.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardResponse(500, "Registration failed: " + e.getMessage(), null));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<StandardResponse> loginUser(
            @Valid @RequestBody UserLoginRequestDTO requestDTO)
    {
        UserRegistrationResponseDTO response = userService.loginUser(requestDTO);
        return ResponseEntity.ok(new StandardResponse(200, "User Logged In Successfully", response));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<StandardResponse> forgotPassword(
            @RequestParam String username)
    {
        String resetToken = userService.forgotPassword(username);
        return ResponseEntity.ok(new StandardResponse(200, "Password reset token generated.", resetToken));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<StandardResponse> resetPassword(
            @RequestParam String token,
            @RequestBody @Valid ResetPasswordRequestDTO requestDTO) {
        userService.resetPassword(token, requestDTO);
        return ResponseEntity.ok(new StandardResponse(200, "Password reset successfully.", null));
    }




    @GetMapping("/admin/get-by-id")
     //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardResponse> getUserById(
            @RequestParam Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(new StandardResponse(200, "User retrieved successfully", user));
    }




    @DeleteMapping("/admin/delete-by-id")
    //  @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardResponse> deleteUser(
            @RequestParam Long id)
    {
        userService.deleteUser(id);  // Call service method to delete the user
        return ResponseEntity.ok(new StandardResponse(200, "User deleted successfully", null));
    }


/*end points for user history management*/

// Retrieve Purchase History for Tourist

    @GetMapping("/purchase-history")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'TOURIST')")
    public ResponseEntity<StandardResponse> getTouristPurchaseHistory(
            @RequestParam Long userId)
    {
        List<UserPurchaseHistoryResponseDTO> purchaseHistory = userPurchaseHistoryService.getPurchaseHistoriesForUser(userId);
        return ResponseEntity.ok(new StandardResponse(200, "Tourist Purchase History Retrieved", purchaseHistory));
    }


    // Retrieve Booking History for Tourist

    @GetMapping("/booking-history")
    // @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'TOURIST')")
    public ResponseEntity<StandardResponse> getTouristBookingHistory(
            @RequestParam Long userId)
    {
        List<UserBookingHistoryResponseDTO> bookingHistory = userBookingHistoryService.getBookingHistoriesForUser(userId);
        return ResponseEntity.ok(new StandardResponse(200, "Tourist Booking History Retrieved", bookingHistory));
    }


    // Purchase History Endpoint
    @GetMapping("/admin/purchaseHistory")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserPurchaseHistoryResponseDTO> getPurchaseHistories(
            @RequestParam Long userId,
            @RequestParam String role,
            @RequestParam(required = false) Long productId) {
        return userPurchaseHistoryService.getPurchaseHistoriesBasedOnRole(userId, role, productId);
    }

//    // Booking History Endpoint
    @GetMapping("/admin/bookingHistory")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserBookingHistoryResponseDTO> getBookingHistories(
            @RequestParam Long userId,
            @RequestParam String role)
             {
        return userBookingHistoryService.getBookingHistoriesBasedOnRole(userId, role);
    }




@PutMapping("/update-profile")
public ResponseEntity<StandardResponse> updateUserProfile(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) String cfId,
        @RequestBody UserUpdateRequestDTO requestDTO) {

    if (userId == null && cfId == null) {
        throw new IllegalArgumentException("Either userId or cfId must be provided");
    }

    UserResponseDTO updatedUser;
    if (userId != null) {
        updatedUser = userService.updateUserProfile(userId, requestDTO);
    } else {
        updatedUser = userService.updateUserProfileByCfId(cfId, requestDTO);
    }

    return ResponseEntity.ok(new StandardResponse(200, "User updated successfully", updatedUser));
}

    @GetMapping("/get-by-cfid")
    public ResponseEntity<StandardResponse> getUserByCfId(@RequestParam String cfId) {
        UserResponseDTO user = userService.getUserByCfId(cfId);
        return ResponseEntity.ok(new StandardResponse(200, "User retrieved successfully by cfId", user));
    }

}
