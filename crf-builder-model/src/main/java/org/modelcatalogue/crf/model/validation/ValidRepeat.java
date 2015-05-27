package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidRepeatConstraint.class)

public @interface ValidRepeat {
    String message() default "Maximum number of repeats must be greater or equal to repeat count.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
