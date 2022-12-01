 package team017.global.error;

 import javax.validation.ConstraintViolationException;

 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.HttpRequestMethodNotSupportedException;
 import org.springframework.web.bind.MethodArgumentNotValidException;
 import org.springframework.web.bind.MissingServletRequestParameterException;
 import org.springframework.web.bind.annotation.ExceptionHandler;
 import org.springframework.web.bind.annotation.ResponseStatus;
 import org.springframework.web.bind.annotation.RestControllerAdvice;

 import lombok.extern.slf4j.Slf4j;
 import team017.global.Exception.BusinessLogicException;

 @RestControllerAdvice
 @Slf4j
 public class GlobalExceptionAdvice {
 	/* DTO 클래스의 유효성 검증에서 발생하는 예외 */
 	@ExceptionHandler
 	@ResponseStatus(HttpStatus.BAD_REQUEST)
 	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
 		final ErrorResponse response = ErrorResponse.of(exception.getBindingResult());

 		return response;
 	}

 	/* URI 의 변수 값 검증에서 발생하는 예외 */
 	@ExceptionHandler
 	@ResponseStatus(HttpStatus.BAD_REQUEST)
 	public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
 		final ErrorResponse response = ErrorResponse.of(exception.getConstraintViolations());

 		return response;
 	}

 	/* Not Found */
 	// @ExceptionHandler
 	// @ResponseStatus(HttpStatus.NOT_FOUND)
 	// public ErrorResponse handleResourceNotFoundException(RuntimeException exception) {
 	// 	log.error("# NOT FOUND : {}", exception);
 	// 	final ErrorResponse response = ErrorResponse.of(HttpStatus.NOT_FOUND, exception.getMessage());
	 //
 	// 	return response;
 	// }

 	/* BusinessLogin Exception */
 	@ExceptionHandler
 	public ResponseEntity handleBusinessLogicException(BusinessLogicException exception) {
 		final ErrorResponse response = ErrorResponse.of(exception.getExceptionCode());

 		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
 	}

 	/* Http Method Exception */
 	@ExceptionHandler
 	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
 	public ErrorResponse handleHttpRequestMethodNotSupportException(HttpRequestMethodNotSupportedException exception) {
 		final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage());

 		return response;
 	}

 	/* 요구하는 파라미터 미 입력 예외 */
 	@ExceptionHandler
 	@ResponseStatus(HttpStatus.BAD_REQUEST)
 	public ErrorResponse handleMissingRequestParameterException(MissingServletRequestParameterException exception) {
 		final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.getMessage());

 		return response;
 	}

 	/* 그 외의 500 예외 */
 	@ExceptionHandler
 	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
 	public ErrorResponse handleException(Exception exception) {
 		log.error("# handle Exception : ", exception);
 		final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

 		return response;
 	}
 }

