package com.healthcare.user_service.request;

import com.healthcare.user_service.validator.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Username is required")
    @Size(min = 6, message = "Username must be at least 6 characters")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must be at least 8 characters and contain at least one letter and one number")
    @NotNull(message = "Password is required")
    private String password;
    @Email(message = "Email should be valid")
    private String email;
    @ValidRole(message = "Role is required, it must be 'ADMIN', 'PROFESSIONNELDESANTE', or 'AGENTADMINISTRATIF'")
    private String role ;
}
