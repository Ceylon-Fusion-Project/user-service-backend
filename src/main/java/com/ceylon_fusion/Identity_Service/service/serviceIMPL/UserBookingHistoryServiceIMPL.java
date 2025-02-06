package com.ceylon_fusion.Identity_Service.service.serviceIMPL;

import com.ceylon_fusion.Identity_Service.dto.response.UserBookingHistoryResponseDTO;
import com.ceylon_fusion.Identity_Service.entity.UserBookingHistory;
import com.ceylon_fusion.Identity_Service.exception.UnauthorizedException;
import com.ceylon_fusion.Identity_Service.repo.UserBookingHistoryRepo;
import com.ceylon_fusion.Identity_Service.repo.UserRepo;
import com.ceylon_fusion.Identity_Service.service.UserBookingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBookingHistoryServiceIMPL implements UserBookingHistoryService {

    @Autowired
    private UserBookingHistoryRepo bookingHistoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<UserBookingHistoryResponseDTO> getBookingHistoriesForUser(Long userId) {
        // Fetch booking histories using the corrected repository method
        List<UserBookingHistory> histories = bookingHistoryRepo.findByUser_UserId(userId);

        // Convert to response DTOs
        return histories.stream()
                .map(UserBookingHistoryResponseDTO::new)
                .collect(Collectors.toList());
    }



    @Override
    public List<UserBookingHistoryResponseDTO> getBookingHistoriesBasedOnRole(Long userId, String role) {
        switch (role) {
            case "TOURIST":
                // Fetch only the personal booking history for the user
                return getBookingHistoriesForUser(userId);

            case "ADMIN":
                // Fetch all booking histories
                return getAllBookingHistories();
            default:
                throw new UnauthorizedException("Role not supported");
        }
    }



    // Fetch all booking histories for admin
    private List<UserBookingHistoryResponseDTO> getAllBookingHistories() {
        List<UserBookingHistory> histories = bookingHistoryRepo.findAll();
        return histories.stream()
                .map(UserBookingHistoryResponseDTO::new)
                .collect(Collectors.toList());
    }
}
