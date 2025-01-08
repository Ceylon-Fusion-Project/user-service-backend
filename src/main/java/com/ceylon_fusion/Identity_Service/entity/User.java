package com.ceylon_fusion.Identity_Service.entity;

import com.ceylon_fusion.Identity_Service.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name",nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Store as a string in the database
    private Role role;// User roles like ADMIN, BUYER,SELLER etc.

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationship with UserPurchaseHistory
    @OneToMany(mappedBy = "user")
    private List<UserPurchaseHistory> purchaseHistories;

    // Relationship with UserBookingHistory
    @OneToMany(mappedBy = "user")
    private List<UserBookingHistory> bookingHistories;

}
