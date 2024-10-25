package site.beyondchasm.teambasketball.auth.controller;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.auth.domain.OauthRequestDto;
import site.beyondchasm.teambasketball.auth.domain.OauthResponseDto;
import site.beyondchasm.teambasketball.auth.domain.RefreshTokenResponseDto;
import site.beyondchasm.teambasketball.auth.service.OauthService;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthController {

  private final OauthService oauthService;

  /**
   * OAuth를 통한 로그인
   *
   * @param provider        OAuth 제공자 (예: kakao, google)
   * @param oauthRequestDto OAuth 요청 데이터 (accessToken 등)
   * @param response        HttpServletResponse 객체 (쿠키 설정 등)
   * @return OauthResponseDto 로그인 결과 데이터
   */
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
   * 리프레시 토큰을 이용한 액세스 토큰 재발급
   *
   * @param request HttpServletRequest 객체 (쿠키 추출)
   * @return RefreshTokenResponseDto 재발급된 액세스 토큰
   */
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
