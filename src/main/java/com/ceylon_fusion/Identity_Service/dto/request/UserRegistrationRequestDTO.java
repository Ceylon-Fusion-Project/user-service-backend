package com.ceylon_fusion.Identity_Service.dto.request;


import com.ceylon_fusion.Identity_Service.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.ISourceLocation;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationRequestDTO {

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Email
    private String email;


    @NotNull
    private Role role;

    @NotNull
    @Size(min = 3, max = 50, message = "CF ID must be between 3 and 50 characters")
    private String cfId;

    @NotNull
    @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
    private String country;

    @NotNull
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    @NotNull
   // @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotNull
    @Size(min = 3, max = 50, message = "Currency must be between 3 and 50 characters")
    private String currency;

    @Size(min = 0, max = 100, message = "City must be between 0 and 100 characters")
    private String city;

    @Size(min = 0, max = 100, message = "State must be between 0 and 100 characters")
    private String state;

    @NotNull
    @Size(min = 5, max = 10, message = "Zip code must be between 5 and 10 characters")
    private String zipCode;

    @Size(min = 0, max = 255, message = "Product preferences must be between 0 and 255 characters")
    private List<String> preferredCategories;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
  
    public List<String> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(List<String> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    public String getCity() {
        return city;

    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getCfId() {
        return cfId;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public List<String> getPreferredCategories() {
        return preferredCategories;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }
}
