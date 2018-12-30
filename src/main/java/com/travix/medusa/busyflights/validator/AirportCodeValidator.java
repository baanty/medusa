package com.travix.medusa.busyflights.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AirportCodeValidatorImpl.class )
@Documented
public @interface AirportCodeValidator {
	String message() default "Airport Code must be an uppercase three letter alphabetic code";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
