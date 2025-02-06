package com.ceylon_fusion.Identity_Service.service.serviceIMPL;

import com.ceylon_fusion.Identity_Service.dto.response.UserPurchaseHistoryResponseDTO;
import com.ceylon_fusion.Identity_Service.entity.User;
import com.ceylon_fusion.Identity_Service.entity.UserPurchaseHistory;
import com.ceylon_fusion.Identity_Service.exception.ResourceNotFoundException;
import com.ceylon_fusion.Identity_Service.exception.UnauthorizedException;
import com.ceylon_fusion.Identity_Service.repo.UserPurchaseHistoryRepo;
import com.ceylon_fusion.Identity_Service.repo.UserRepo;
import com.ceylon_fusion.Identity_Service.service.UserPurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPurchaseHistoryServiceIMPL implements UserPurchaseHistoryService {
    @Autowired
    private UserPurchaseHistoryRepo purchaseHistoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Override

    public List<UserPurchaseHistoryResponseDTO> getPurchaseHistoriesForUser(Long userId) {
        // Validate if the user exists
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + userId));

        // Retrieve purchase history for the user using the correct repository method
        List<UserPurchaseHistory> histories = purchaseHistoryRepo.findByUser_UserId(userId);

        // Map entities to response DTOs
        return histories.stream()
                .map(UserPurchaseHistoryResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    public List<UserPurchaseHistoryResponseDTO> getPurchaseHistoriesBasedOnRole(Long userId, String role, Long productId) {
        switch (role) {
            case "TOURIST":
                // Fetch only the personal purchase history for the user
                return getPurchaseHistoriesForUser(userId);
            case "SELLER":
                // Fetch purchase histories based on product ID
                return getPurchaseHistoriesForSeller(productId);
            case "ADMIN":
                // Fetch all purchase histories
                return getAllPurchaseHistories();
            default:
                throw new UnauthorizedException("Role not supported");
        }
    }

    // Fetch purchase histories for a specific product owned by a seller
    private List<UserPurchaseHistoryResponseDTO> getPurchaseHistoriesForSeller(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID must be provided for seller role");
        }

        // Retrieve purchase histories by product ID
        List<UserPurchaseHistory> histories = purchaseHistoryRepo.findByOrderId(productId); // Assuming orderId == productId

        return histories.stream()
                .map(UserPurchaseHistoryResponseDTO::new)
                .collect(Collectors.toList());
    }



    // Fetch all purchase histories for admin
    private List<UserPurchaseHistoryResponseDTO> getAllPurchaseHistories() {
        List<UserPurchaseHistory> histories = purchaseHistoryRepo.findAll();
        return histories.stream()
                .map(UserPurchaseHistoryResponseDTO::new)
                .collect(Collectors.toList());
    }


}
