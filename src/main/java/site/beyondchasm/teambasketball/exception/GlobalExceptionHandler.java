package site.beyondchasm.teambasketball.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		logger.error("[CustomException] errCode : " + ex.getErrorCode());
		logger.error("[CustomException] errMsg : " + ex.getMessage(), ex); // 스택 트레이스 추가
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), ex.getErrorCode().getHttpStatus());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
		logger.error("[RuntimeException] errMsg : " + ex.getMessage(), ex); // 스택 트레이스 추가
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) { // 파라미터 타입 수정
		logger.error("[Exception] errMsg : " + ex.getMessage(), ex); // 스택 트레이스 추가
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
