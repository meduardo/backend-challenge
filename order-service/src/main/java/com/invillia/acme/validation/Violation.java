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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageKey == null) ? 0 : messageKey.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Violation other = (Violation) obj;
		if (messageKey == null) {
			if (other.messageKey != null)
				return false;
		} else if (!messageKey.equals(other.messageKey))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
}
