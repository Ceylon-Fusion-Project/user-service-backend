package com.ceylon_fusion.Identity_Service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryRequestDTO {
    private Long orderId; // Order ID provided by the user
    private LocalDateTime orderDate;
}
