package com.invillia.acme.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permite executar uma sequência de validações desejadas, de forma declarativa,
 * especificando qual status code deve ser retornado.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 */
public class Validator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

	private static final IntPredicate IS_HTTP_STATUS_ERROR = status -> status >= 400 && status < 600; 
	private static final String NOT_STATUS_ERROR_MSG = "Error creating validations, httpStatusCode [%s] is not a valid status error! Only 4xx, 5xx expected, verify!";
	
	private final Collection<Validation> validations;
	private final int httpStatusCode;
	
	private Validator(final Collection<Validation> validations, final int httpStatusCode) {
		this.validations = validations;
		this.httpStatusCode = httpStatusCode;
	}
	
	public void execute() {
		if(validations.isEmpty()) {
			return;
		}
		
		Set<Violation> errors = processValidations();
		
		if(errors.isEmpty()) {
			return;
		}

		LOGGER.error("Error(s) in validation - {}", errors);

		throw BusinessException.with(errors, httpStatusCode); 

	}
	
	private final Set<Violation> processValidations() {
		return validations.stream()
						  .map(Validation::process)
						  .filter(Optional::isPresent)
						  .map(Optional::get)
						  .collect(Collectors.toSet());
	}
	
	private static void requireErrorCode(final int httpStatusCode) {
		if (IS_HTTP_STATUS_ERROR.test(httpStatusCode)){
			return;
		}

		throw new IllegalArgumentException(String.format(NOT_STATUS_ERROR_MSG, httpStatusCode));
	}
	
	//TODO Crar um builder para deixar o código mais fluente
	public static final Validator with(final int httpStatusCode, Validation... validations) {
		
		requireErrorCode(httpStatusCode);

		return new Validator(Optional.ofNullable(validations)
							   	     .map(Arrays::asList)
									 .orElseGet(ArrayList::new),
						     httpStatusCode
		);
	}
}
