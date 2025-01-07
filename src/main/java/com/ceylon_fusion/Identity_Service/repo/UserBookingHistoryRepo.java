package com.ceylon_fusion.Identity_Service.repo;

import com.ceylon_fusion.Identity_Service.entity.User;
import com.ceylon_fusion.Identity_Service.entity.UserBookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookingHistoryRepo extends JpaRepository<UserBookingHistory, Long> {
    // Find all booking history by user
    List<UserBookingHistory> findByUser(User user);

    // Find booking history by user ID
    List<UserBookingHistory> findByUser_UserId(Long userId);
}
