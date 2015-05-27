package org.modelcatalogue.crf.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidWidthDecimalConstraint.class)

public @interface ValidWidthDecimal {
    String message() default "Width decimal is only supported for data types REAL, INT a ST and must be in format w(d). See CRF instructions for details";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
