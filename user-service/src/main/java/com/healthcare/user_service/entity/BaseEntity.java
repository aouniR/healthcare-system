package com.healthcare.user_service.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable{

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime updateTimestamp; 
}
