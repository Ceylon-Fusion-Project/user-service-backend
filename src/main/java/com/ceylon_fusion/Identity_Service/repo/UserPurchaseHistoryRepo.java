package com.ceylon_fusion.Identity_Service.repo;

import com.ceylon_fusion.Identity_Service.entity.User;
import com.ceylon_fusion.Identity_Service.entity.UserPurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPurchaseHistoryRepo extends JpaRepository<UserPurchaseHistory, Long> {
    // Find all purchase history by user
    List<UserPurchaseHistory> findByUser(User user);

    // Find purchase history by user ID
    List<UserPurchaseHistory> findByUser_UserId(Long userId);
}
