package com.onlinepharma.onlinepharma.utils.validatore;

import com.onlinepharma.onlinepharma.annotations.UniquePhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }

}
