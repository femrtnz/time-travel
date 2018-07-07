package co.uk.timetravel.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,9}")
@NotEmpty
@ReportAsSingleViolation
public @interface PGIValid {
    String message() default "Invalid PGI";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}