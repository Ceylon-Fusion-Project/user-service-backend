package com.ceylon_fusion.Identity_Service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRecordRequestDTO {
    private Long user_booking_his_id; // Required
    private LocalDateTime bookingDate; // Optional
}
