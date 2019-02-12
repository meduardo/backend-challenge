package com.invillia.acme.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Representa a violação de alguma regra.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
public class Violation {

	private final String messageKey;
	
	private final String path;
	
	private final List<String> params;
	
	private Violation(final String messageKey, final String path, final List<String> params) {
		this.messageKey = messageKey;
		this.path = path;
		this.params = params;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public String getPath() {
		return path;
	}

	public List<String> getParams() {
		return params;
	}
	
	private static <T> T requireNonNull(T obj, String name) {
		return Objects.requireNonNull(obj, name + " can not be null (must be informed)! Verify!");
	}
	
	public static final Violation of(final String messageKey, final String path, final String... messageParams) {
		List<String> params = Optional.ofNullable(messageParams)
								      .map(Arrays::asList)
								      .orElseGet(Collections::emptyList);
								    
		return new Violation(requireNonNull(messageKey, "Bundle message key"), 
							 requireNonNull(path, "Validated field"), 
							 params);
	}
	
}
