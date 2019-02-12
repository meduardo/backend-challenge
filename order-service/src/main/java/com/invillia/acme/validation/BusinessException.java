package com.invillia.acme.validation;

import java.util.Objects;
import java.util.Set;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */

// TODO criar um controller advice para capturar e montar o retorno de erro com base nos erros, e no status code.

public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private final Set<Violation> errors;
	
	private final int httpStatusCode;

	private BusinessException(final Set<Violation> errors, final int httpStatusCode) {
		this.errors = errors;
		this.httpStatusCode = httpStatusCode;
	}

	public final Set<Violation> violations() {
		return errors;
	}

	public final int httpStatus() {
		return httpStatusCode;
	}
	
	public static final BusinessException with(final Set<Violation> errors, final int httpStatusCode) {
		Objects.requireNonNull(errors, "Business errors can not be null! Verify!");
		
		
		return new BusinessException(errors, httpStatusCode);
	}

	
}
