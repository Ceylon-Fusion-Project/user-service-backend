package com.ceylon_fusion.Identity_Service.service;

import com.ceylon_fusion.Identity_Service.dto.request.PurchaseHistoryRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserPurchaseHistoryResponseDTO;

import java.util.List;

public interface UserPurchaseHistoryService {


    List<UserPurchaseHistoryResponseDTO> getPurchaseHistoriesForUser(Long userId);


    List<UserPurchaseHistoryResponseDTO> getPurchaseHistoriesBasedOnRole(Long userId, String role, Long productId);
}
