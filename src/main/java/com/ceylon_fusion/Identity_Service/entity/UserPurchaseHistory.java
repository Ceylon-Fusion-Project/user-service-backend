package com.ceylon_fusion.Identity_Service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="user_purchase_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_purchase_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "order_id", nullable = false)
    @NotNull
    private Long orderId;

    @Column(name = "purchased_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime purchasedDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
}
