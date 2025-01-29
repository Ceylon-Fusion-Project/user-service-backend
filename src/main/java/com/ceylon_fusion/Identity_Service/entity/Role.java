package com.ceylon_fusion.Identity_Service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="roles")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName;
}
