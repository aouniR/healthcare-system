package com.healthcare.user_service.entity;

import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;


@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity(name="users")
@Getter
@Setter
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    @Column(unique = true, nullable = false,updatable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;  
}
