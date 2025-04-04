package com.ceylon_fusion.Identity_Service.repo;

import com.ceylon_fusion.Identity_Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if user exists by username
    boolean existsByUsername(String username);

    // Check if user exists by email
    boolean existsByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

    Optional<User> findByCfId(String cfId);


    boolean existsByPhoneNumber(long phoneNumber);
}
