package com.ceylon_fusion.Identity_Service.service;

import com.ceylon_fusion.Identity_Service.dto.request.BookingRecordRequestDTO;
import com.ceylon_fusion.Identity_Service.dto.response.UserBookingHistoryResponseDTO;

import java.util.List;

public interface UserBookingHistoryService {


    List<UserBookingHistoryResponseDTO> getBookingHistoriesForUser(Long userId);


    List<UserBookingHistoryResponseDTO> getBookingHistoriesBasedOnRole(Long userId, String role);


}
