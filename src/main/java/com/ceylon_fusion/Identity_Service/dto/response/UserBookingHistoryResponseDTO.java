package com.ceylon_fusion.Identity_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookingHistoryResponseDTO {
    private Long id;
    private Long bookingId; // FK to Booking Service
    private LocalDateTime bookedDate;
    private String packageTitle;
    private Double totalCost;
}
