package com.healthcare.user_service.entity;

import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;


@Data
@Table
@Builder
@Entity
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userid;
    private String username;
    @Column(unique = true, nullable = false,updatable = false, name="Email")
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private TypeProfil typeProfil;  
    
}
