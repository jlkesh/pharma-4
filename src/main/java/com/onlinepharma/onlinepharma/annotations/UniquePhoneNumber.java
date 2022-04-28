package com.onlinepharma.onlinepharma.annotations;


import com.onlinepharma.onlinepharma.utils.validatore.UniquePhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UniquePhoneNumberValidator.class})
public @interface UniquePhoneNumber {

    String message() default "phone number must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
