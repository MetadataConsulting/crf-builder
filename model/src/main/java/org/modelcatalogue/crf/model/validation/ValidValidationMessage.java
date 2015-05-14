package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidValidationMessageConstraint.class)

public @interface ValidValidationMessage {
    String message() default "You have to provide validation message if add validation to the item.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
