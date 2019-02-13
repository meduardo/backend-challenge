package com.invillia.acme.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.invillia.acme.validation.BusinessException;
import com.invillia.acme.validation.Violation;

/**
 * Permite tratar s exceções devolvidas ao cliente, como desejado
 * em m ponto único.
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {


	  @ExceptionHandler(BusinessException.class)
	  public ResponseEntity<ErrorsDTO> businessEception(final BusinessException exc) {
		  
		  Set<ErrorInfo> errors = exc.violations()
				  	 				 .stream()
				  	 				 .map(ErrorInfo::valueOf)
				  	 				 .collect(Collectors.toSet());
		  
		  return new ResponseEntity<>(new ErrorsDTO(exc.httpStatus(), errors), HttpStatus.valueOf(exc.httpStatus()));
	  }


	  public static class ErrorsDTO {
		  
		  private final int status;
		  private final Set<ErrorInfo> errors;

		  public ErrorsDTO(final int status, final Set<ErrorInfo> errors) {
			  this.status = status;
			  this.errors = errors;
		  }

		  public Set<ErrorInfo> getErrors() {
			  return errors;
		  }

		  public int getStatus() {
			  return status;
		  }

	  }
	  
	  public static class ErrorInfo {
		  
		  private final String message;

		  private final String field;

		  public ErrorInfo(final String message, final String field) {
			  this.message = message;
			  this.field = field;
		  }

		  public static final ErrorInfo valueOf(final Violation error) {
			  //TODO usar um bundle para monter a mensagem de erro com base na chave, e nos params.
			  return new ErrorInfo(
					  	error.getMessageKey(),
					  	error.getPath()
					 );
		  }
		  public String getMessage() {
			  return message;
		  }

		  public String getField() {
			  return field;
		  }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((field == null) ? 0 : field.hashCode());
			result = prime * result + ((message == null) ? 0 : message.hashCode());
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
			ErrorInfo other = (ErrorInfo) obj;
			if (field == null) {
				if (other.field != null)
					return false;
			} else if (!field.equals(other.field))
				return false;
			if (message == null) {
				if (other.message != null)
					return false;
			} else if (!message.equals(other.message))
				return false;
			return true;
		}
		  
	  }
}