package com.invillia.acme.validation;

import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 */ 
public class Validation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Validation.class);
	
	private final Rule rule;
	
	private final Supplier<Violation> onFail;
	
	private Validation(final Rule rule, final Supplier<Violation> onFail) {
		this.rule = rule;
		this.onFail = onFail;
	}
	
	public final Optional<Violation> process() {
		try {
			return rule.assertTrue() ? 
						Optional.empty() :
						Optional.ofNullable(onFail.get());
			
		} catch (Exception e) {
			LOGGER.error("Validation error occurred: ", e);
			return Optional.ofNullable(onFail.get());
		}
	}
	
	public static final ValidationBuilder rule(final Rule rule) {
		return new ValidationBuilder(rule);
	}
	
	public static final class ValidationBuilder {
		
		private final Rule rule;
		
		public ValidationBuilder(final Rule rule) {
			this.rule = rule;
		}
		
		public final Validation onFail(final Supplier<Violation> onFail) {
			return new Validation(rule, onFail);
		} 
	}

}
