package com.onlinepharma.onlinepharma.utils.validatore;

import com.onlinepharma.onlinepharma.annotations.Unique;
import com.onlinepharma.onlinepharma.enums.annotations.FieldType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

    private FieldType type;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.type = constraintAnnotation.fieldType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (type) {
            case USERNAME -> checkUsername(value);
            case PASSPORT_SERIAL -> checkPassportSerial(value);
            case PHONE_NUMBER -> checkPhoneNumber(value);
            case DEFAULT -> true;
        };
    }

    private boolean checkUsername(String value) {
        return false;
    }

    private boolean checkPassportSerial(String value) {
        return false;
    }

    private boolean checkPhoneNumber(String value) {
        return false;
    }

}
