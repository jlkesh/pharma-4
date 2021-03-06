package com.onlinepharma.onlinepharma.annotations;

import com.onlinepharma.onlinepharma.enums.annotations.FieldType;
import com.onlinepharma.onlinepharma.utils.validatore.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueValidator.class})
@NotNull
@Inherited
public @interface Unique {
    FieldType fieldType() default FieldType.DEFAULT;

    String message() default "field must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
