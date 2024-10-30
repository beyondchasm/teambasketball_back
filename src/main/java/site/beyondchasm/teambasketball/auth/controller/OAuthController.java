package site.beyondchasm.teambasketball.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.beyondchasm.teambasketball.auth.domain.OauthRequestDto;
import site.beyondchasm.teambasketball.auth.domain.OauthResponseDto;
import site.beyondchasm.teambasketball.auth.domain.RefreshTokenResponseDto;
import site.beyondchasm.teambasketball.auth.service.OauthService;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;

/**
 * OAuth 인증과 토큰 관리를 처리하는 컨트롤러 클래스.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthController {

  private final OauthService oauthService;

  /**
   * 지정된 OAuth 제공자를 통한 로그인 메서드.
   *
   * @param provider        OAuth 제공자 (예: kakao, google)
   * @param oauthRequestDto OAuth 요청 데이터 (accessToken 등)
   * @param response        HttpServletResponse 객체 (쿠키 설정 등)
   * @return OauthResponseDto 로그인 결과 데이터
   * @throws CustomException 잘못된 OAuth 제공자가 주어진 경우
   */
  @Operation(summary = "OAuth를 통한 로그인", description = "지정된 OAuth 제공자 (예: kakao, google)를 통해 로그인합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로그인 성공"),
      @ApiResponse(responseCode = "400", description = "잘못된 OAuth 제공자"),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PostMapping("/oauth/{provider}")
  public ResponseEntity<OauthResponseDto> login(
      @PathVariable String provider,
      @RequestBody OauthRequestDto oauthRequestDto,
      HttpServletResponse response) {

    OauthResponseDto oauthResponseDto = switch (provider.toLowerCase()) {
      case "kakao" -> oauthService.loginWithKakao(oauthRequestDto.getAccessToken(), response);
      case "google" -> oauthService.loginWithGoogle(oauthRequestDto.getAccessToken(), response);
      default -> throw new CustomException(ErrorCode.INVALID_OAUTH_PROVIDER);
    };

    return new ResponseEntity<>(oauthResponseDto, HttpStatus.OK);
  }

  /**
   * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 재발급하는 메서드.
   *
   * @param request HttpServletRequest 객체 (쿠키에서 리프레시 토큰 추출)
   * @return RefreshTokenResponseDto 재발급된 액세스 토큰
   * @throws CustomException 유효하지 않은 리프레시 토큰인 경우
   */
  @Operation(summary = "리프레시 토큰을 이용한 액세스 토큰 재발급", description = "저장된 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "액세스 토큰 재발급 성공"),
      @ApiResponse(responseCode = "400", description = "유효하지 않은 리프레시 토큰"),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PostMapping("/tokens/refresh")
  public ResponseEntity<RefreshTokenResponseDto> refreshToken(HttpServletRequest request) {
    RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();

    Optional<Cookie> refreshTokenCookieOpt = Arrays.stream(
            Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
        .filter(cookie -> "refresh_token".equals(cookie.getName()))
        .findFirst();

    if (refreshTokenCookieOpt.isEmpty()) {
      throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    String refreshToken = refreshTokenCookieOpt.get().getValue();
    String accessToken = oauthService.refreshAccessToken(refreshToken);

    refreshTokenResponseDto.setAccessToken(accessToken);
    return new ResponseEntity<>(refreshTokenResponseDto, HttpStatus.OK);
  }
}
