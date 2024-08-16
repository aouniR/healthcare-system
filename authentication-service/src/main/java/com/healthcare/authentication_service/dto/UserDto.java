package com.healthcare.authentication_service.dto;

import java.util.UUID;
import com.healthcare.authentication_service.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private Role role;
}
