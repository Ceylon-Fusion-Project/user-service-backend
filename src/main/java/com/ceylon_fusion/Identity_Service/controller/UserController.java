package com.ceylon_fusion.Identity_Service.controller;

import com.ceylon_fusion.Identity_Service.dto.request.*;
import com.ceylon_fusion.Identity_Service.dto.response.UserBookingHistoryResponseDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserPurchaseHistoryResponseDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserRegistrationResponseDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserResponseDTO;
import com.ceylon_fusion.Identity_Service.exception.EmailAlreadyExistsException;
import com.ceylon_fusion.Identity_Service.service.UserBookingHistoryService;
import com.ceylon_fusion.Identity_Service.service.UserPurchaseHistoryService;
import com.ceylon_fusion.Identity_Service.service.UserService;
import com.ceylon_fusion.Identity_Service.util.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPurchaseHistoryService userPurchaseHistoryService;

    @Autowired
    private UserBookingHistoryService userBookingHistoryService;

    @PostMapping("/register")
    public ResponseEntity<StandardResponse> registerUser(@RequestBody @Valid UserRegistrationRequestDTO requestDTO) {
        try {
            // Call service to handle registration logic
            UserRegistrationResponseDTO response = userService.registerUser(requestDTO);

            // Return success response
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new StandardResponse(201, "User Registered Successfully", response));
        } catch (EmailAlreadyExistsException e) {
            // Handle duplicate email exception
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new StandardResponse(409, "Email is already in use.", null));
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardResponse(500, "An unexpected error occurred.", null));
        }}


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

//    @PutMapping("/user/update-profile")
//    public ResponseEntity<StandardResponse> updateUserProfile(
//            @RequestParam Long id,
//            @RequestPart("userData") UserUpdateRequestDTO requestDTO,
//            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) {
//
//        // Set the profile photo if provided
//        if (profilePhoto != null) {
//            requestDTO.setProfilePhoto(profilePhoto);
//        }
//
//        UserResponseDTO updatedUser = userService.updateUserProfile(id, requestDTO);
//        return ResponseEntity.ok(new StandardResponse(200, "Profile updated successfully", updatedUser));
//    }

    @GetMapping("/admin/get-by-id")
     //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardResponse> getUserById(
            @RequestParam Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(new StandardResponse(200, "User retrieved successfully", user));
    }

@PutMapping("/admin/update-by-id")
//  @PreAuthorize("hasAuthority('ADMIN')")
public ResponseEntity<StandardResponse> updateUser(
        @RequestParam Long id,
        @RequestBody UserUpdateRequestDTO requestDTO)
{
    UserResponseDTO updatedUser = userService.updateUser(id, requestDTO);
    return ResponseEntity.ok(new StandardResponse(200, "User updated successfully", updatedUser));
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




    }
