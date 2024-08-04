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
    private UUID id;
    @Column(name="userName")
    private String username;
    @Column(name="Email")
    private String email;
    @Column(name="peassword")
    private String password;

}
