package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^[\\pL\\pN\\s_\\-\\.,;]*$", message = "can only contain alphanumerical characters")
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface AlphaNumeric {
    String message() default "can only contain alphanumerical characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
