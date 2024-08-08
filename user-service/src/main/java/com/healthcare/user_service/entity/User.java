package com.healthcare.user_service.entity;

import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;

@Data
@Table
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="userID")
    private UUID userid;
    @Column(name="firstName")
    private String firstname;
    @Column(name="lastName")
    private String lastname;
    @Column(name="Email")
    private String email;
    @Column(name="peassword")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private TypeProfil typeProfil;
    
}
