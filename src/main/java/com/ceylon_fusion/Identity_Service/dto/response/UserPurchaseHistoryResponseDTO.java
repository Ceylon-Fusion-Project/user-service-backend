package com.ceylon_fusion.Identity_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchaseHistoryResponseDTO {
    private Long id;
    private Long orderId; // FK to Order Service
    private LocalDateTime purchasedDate;
    private Double totalAmount;
}
