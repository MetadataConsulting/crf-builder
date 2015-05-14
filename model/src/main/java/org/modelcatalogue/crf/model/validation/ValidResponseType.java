package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidResponseTypeConstraint.class)

public @interface ValidResponseType {
    String message() default "Response type is not valid for given data type.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
