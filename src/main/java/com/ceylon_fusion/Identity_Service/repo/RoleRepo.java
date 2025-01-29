package com.ceylon_fusion.Identity_Service.repo;

import com.ceylon_fusion.Identity_Service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
