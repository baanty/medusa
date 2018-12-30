package com.travix.medusa.busyflights.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AirportCodeValidatorImpl implements ConstraintValidator<AirportCodeValidator, String> {

	private static final String AIRPORT_CODE_MATCHER = "[A-Z][A-Z][A-Z]";

	@Override
	public void initialize(AirportCodeValidator constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true; // The field is not mandatory.
		}

		return Pattern.compile(AIRPORT_CODE_MATCHER).matcher(value).matches();
	}

}
