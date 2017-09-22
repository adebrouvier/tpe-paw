package ar.edu.itba.paw.webapp.form.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PlayerValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PlayerConstraint {
    String message() default "Invalid player list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
