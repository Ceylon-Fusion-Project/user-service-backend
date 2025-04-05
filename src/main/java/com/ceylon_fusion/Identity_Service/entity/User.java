package com.ceylon_fusion.Identity_Service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ceylon_fusion.Identity_Service.entity.enums.Role;
import java.time.Instant;
import java.time.LocalDateTime;

import java.util.List;



@Entity
@Table(name="users")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cf_id", nullable = false, unique = true)
    private String cfId;

    @Column(name = "user_name",nullable = false, unique = true)
    private String username;



    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name="country" , nullable = false)
    private String country;

    @Column(name="currency"     , nullable = false)
    private String currency;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false, unique = true)  // Must be non-null
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Store as a string in the database
    private Role role;// User roles like ADMIN, TOURIST,SELLER etc.

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "reset_token") // Ensure this matches your database column name
    private String resetToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;


    // Relationship with UserPurchaseHistory
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPurchaseHistory> purchaseHistories;

    // Relationship with UserBookingHistory
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBookingHistory> bookingHistories;



    public List<UserPurchaseHistory> getPurchaseHistories() {
        return purchaseHistories;
    }

    public void setPurchaseHistories(List<UserPurchaseHistory> purchaseHistories) {
        this.purchaseHistories = purchaseHistories;
    }

    public List<UserBookingHistory> getBookingHistories() {
        return bookingHistories;
    }

    public void setBookingHistories(List<UserBookingHistory> bookingHistories) {
        this.bookingHistories = bookingHistories;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
    }

    public void setCity(String city) {
    }

    public void setState(String state) {
    }

    public void setZipCode(String zipCode) {
    }

    public String getEmail() {
        return email;
    }

    public String getCf_id() {
        return "";
    }

    public String getAddress() {
        return "";
    }



    public String getProfilePhotoPath() {
        return "";
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // And ensure you have this getter:
    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setProfilePhotoPath(String fileName) {
    }
}
