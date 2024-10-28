package site.beyondchasm.teambasketball.auth.service;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.auth.domain.OauthResponseDto;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.user.model.UserDto;
import site.beyondchasm.teambasketball.user.service.UserService;

@RequiredArgsConstructor
@Service
public class OauthService {

  private final UserService userService;
  private final JwtTokenService jwtTokenService;
  private final KakaoOauthService kakaoOauthService;

  /**
   * 카카오 로그인 메서드
   *
   * @param accessToken 카카오 API 액세스 토큰
   * @param response    HttpServletResponse 객체
   * @return OauthResponseDto 토큰 정보
   */
  public OauthResponseDto loginWithKakao(String accessToken, HttpServletResponse response) {
    UserDto userDto = kakaoOauthService.getUserProfileByToken(accessToken);
    return getTokens(userDto.getUserId(), response);
  }

  /**
   * 구글 로그인 메서드
   *
   * @param accessToken 구글 API 액세스 토큰
   * @param response    HttpServletResponse 객체
   * @return OauthResponseDto 토큰 정보
   */
  public OauthResponseDto loginWithGoogle(String accessToken, HttpServletResponse response) {
    GoogleOauthService googleOauthService = new GoogleOauthService(userService);
    UserDto userDto = googleOauthService.getUserProfileByToken(accessToken);
    return getTokens(userDto.getUserId(), response);
  }

  /**
   * 액세스 토큰과 리프레시 토큰을 생성합니다.
   *
   * @param userId   유저 ID
   * @param response HttpServletResponse 객체
   * @return OauthResponseDto 토큰 정보
   */
  public OauthResponseDto getTokens(Long userId, HttpServletResponse response) {
    OauthResponseDto oauthResponseDto = new OauthResponseDto();

    final String accessToken = jwtTokenService.createAccessToken(userId.toString());
    final String refreshToken = jwtTokenService.createRefreshToken(userId.toString());

    UserDto userDto = userService.findByUserId(userId);
    userDto.setRefreshToken(refreshToken);
    userService.updateRefreshToken(userDto);

    jwtTokenService.addRefreshTokenToCookie(refreshToken, response);

    oauthResponseDto.setAccessToken(accessToken);
    oauthResponseDto.setRefreshToken(refreshToken);

    return oauthResponseDto;
  }

  /**
   * 리프레시 토큰을 이용하여 새로운 액세스 토큰을 생성합니다.
   *
   * @param refreshToken 리프레시 토큰
   * @return 새로운 액세스 토큰
   */
  public String refreshAccessToken(String refreshToken) {
    UserDto userDto = userService.findByRefreshToken(refreshToken);
    if (userDto == null) {
      throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    if (!jwtTokenService.validateToken(refreshToken)) {
      throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    return jwtTokenService.createAccessToken(userDto.getUserId().toString());
  }
}
