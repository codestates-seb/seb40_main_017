package team017.global.error;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import lombok.Getter;
import team017.global.Exception.ExceptionCode;

@Getter
public class ErrorResponse {
	private int status;
	private String message;
	private List<FieldError> fieldErrors;
	private List<ConstraintViolationError> violationErrors;

	private ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	private ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
		this.fieldErrors = fieldErrors;
		this.violationErrors = violationErrors;
	}
	public static ErrorResponse of(BindingResult bindingResult) {

		return new ErrorResponse(FieldError.of(bindingResult), null);
	}

	public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {

		return new ErrorResponse(null, ConstraintViolationError.of(violations));
	}

	public static ErrorResponse of(ExceptionCode exceptionCode) {

		return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
	}

	public static ErrorResponse of(HttpStatus httpStatus) {

		return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase());
	}

	public static ErrorResponse of(HttpStatus httpStatus, String message) {

		return new ErrorResponse(httpStatus.value(), message);
	}
}
