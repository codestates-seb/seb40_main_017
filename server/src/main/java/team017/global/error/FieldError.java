package team017.global.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class FieldError {
	private String field;
	private Object rejectedValue;
	private String reason;

	private FieldError(String field, Object rejectedValue, String reason) {
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.reason = reason;
	}
	public static List<FieldError> of(BindingResult bindingResult) {
		final List<org.springframework.validation.FieldError> fieldErrorList = bindingResult.getFieldErrors();

		return fieldErrorList.stream()
			.map(error -> new FieldError(error.getField(),
				error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
				error.getDefaultMessage()
			)).collect(Collectors.toList());
	}
}

