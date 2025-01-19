package com.ceylon_fusion.Identity_Service.repo;

import com.ceylon_fusion.Identity_Service.entity.User;
import com.ceylon_fusion.Identity_Service.entity.UserPurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserPurchaseHistoryRepo extends JpaRepository<UserPurchaseHistory, Long> {
    // Find all purchase history by user
    List<UserPurchaseHistory> findByUser_UserId(Long userId);


    List<UserPurchaseHistory> findByOrderId(Long productId);
}
