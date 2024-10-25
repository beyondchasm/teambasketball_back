package site.beyondchasm.teambasketball.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  UNAUTHORIZED("인증되지않은 요청입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_ACCESS_TOKEN("유효하지않은 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_REFRESH_TOKEN("유효하지않은 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED),
  BAD_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST), NOT_EXIST_USER("존재하지 않는 유저입니다.",
      HttpStatus.UNAUTHORIZED), INVALID_OAUTH_PROVIDER("인증되지않은 프로바이더입니다."
      , HttpStatus.UNAUTHORIZED), CODE_NOT_FOUND("코드를 찾을수 없습니다.",
      HttpStatus.NOT_FOUND), UPDATE_FAILED(
      "사용자정보를 업데이트하지 못했습니다.", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}