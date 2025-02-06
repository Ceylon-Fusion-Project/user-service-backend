package com.ceylon_fusion.Identity_Service.dto.response;

import com.ceylon_fusion.Identity_Service.entity.UserBookingHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookingHistoryResponseDTO {
    private Long user_booking_his_id;
    private Long bookingId;
    private LocalDateTime bookedDate;
    private String packageTitle;
    private Double totalCost;

    public UserBookingHistoryResponseDTO(UserBookingHistory booking) {
        this.user_booking_his_id = booking.getId();
        this.bookingId = booking.getBookingId();
        this.bookedDate = booking.getBookedDate();
        this.packageTitle = booking.getPackageTitle();
        this.totalCost = booking.getTotalCost();
    }


}
