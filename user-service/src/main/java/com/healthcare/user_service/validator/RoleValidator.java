package com.healthcare.user_service.validator;

import com.healthcare.user_service.entity.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roleStr, ConstraintValidatorContext context) {
        if (roleStr == null || roleStr.isEmpty()) {
            return false; 
        }

        try {
            Role.valueOf(roleStr.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
}

