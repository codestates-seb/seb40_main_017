package team017.global.error;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import lombok.Getter;

@Getter
public class ConstraintViolationError {
	private String propertyPath;
	private Object rejectedValue;
	private String reason;

	private ConstraintViolationError(String propertyPath, Object rejectedValue, String reason) {
		this.propertyPath = propertyPath;
		this.rejectedValue = rejectedValue;
		this.reason = reason;
	}
	public static List<ConstraintViolationError> of(Set<ConstraintViolation<?>> constraintViolations) {

		return constraintViolations.stream()
			.map(constraintViolation -> new ConstraintViolationError(
				constraintViolation.getPropertyPath().toString(),
				constraintViolation.getInvalidValue().toString(),
				constraintViolation.getMessage())).collect(Collectors.toList());
	}
}
