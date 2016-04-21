package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^[a-zA-Z0-9_\\-\\.,;]*$", message = "can only contain English alphanumerical characters and cannot contain any white spaces")
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface AlphaNumericEnglishNoSpaces {
    String message() default "can only contain English alphanumerical characters and cannot contain any white spaces";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
