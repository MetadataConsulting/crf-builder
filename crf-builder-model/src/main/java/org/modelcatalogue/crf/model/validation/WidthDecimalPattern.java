package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "[w(\\d{1,4})]\\([d(\\d{1,2})]\\)", message = "should look like number(d|number)")
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface WidthDecimalPattern {
    String message() default "should look like number(d|number)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
