package com.healthcare.user_service.dto;

import java.util.UUID;
import com.healthcare.user_service.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private Role role;
}
