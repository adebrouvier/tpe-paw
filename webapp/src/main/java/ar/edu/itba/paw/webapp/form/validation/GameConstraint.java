package ar.edu.itba.paw.webapp.form.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({  METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = GameValidator.class)
@Documented
public @interface GameConstraint {

    String message() default "That game/sport is not allow.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    List<String> annotation = new ArrayList<>();

}
