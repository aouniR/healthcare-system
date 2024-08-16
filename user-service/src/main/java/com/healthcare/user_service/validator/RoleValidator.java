package com.healthcare.user_service.validator;

import com.healthcare.user_service.entity.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {

    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        if (role == null) {
            return false; 
        }
        for (Role r : Role.values()) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }
}

