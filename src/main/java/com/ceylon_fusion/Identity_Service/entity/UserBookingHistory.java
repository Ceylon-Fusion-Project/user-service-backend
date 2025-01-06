package com.ceylon_fusion.Identity_Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="user_booking_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_booking_his_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "booked_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime bookedDate;

    @Column(name = "package_title", nullable = false)
    private String packageTitle;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;


}
