package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidDefaultValueConstraint.class)

public @interface ValidDefaultValue {
    String message() default "Default value not supported for selected data type.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
