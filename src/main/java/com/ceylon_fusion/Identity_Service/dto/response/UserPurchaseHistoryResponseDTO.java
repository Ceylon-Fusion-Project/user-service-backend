package com.ceylon_fusion.Identity_Service.dto.response;

import com.ceylon_fusion.Identity_Service.entity.UserPurchaseHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchaseHistoryResponseDTO {
    private Long user_purchase_his_id;
    private Long orderId;
    private LocalDateTime purchasedDate;
    private Double totalAmount;

    public UserPurchaseHistoryResponseDTO(UserPurchaseHistory order) {
        this.user_purchase_his_id = order.getId();
        this.orderId = order.getOrderId();
        this.purchasedDate = order.getPurchasedDate();
        this.totalAmount = order.getTotalAmount();
    }

}