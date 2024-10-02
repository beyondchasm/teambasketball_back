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

	// 카카오 로그인
	public OauthResponseDto loginWithKakao(String accessToken, HttpServletResponse response) {
		UserDto userDto = kakaoOauthService.getUserProfileByToken(accessToken);
		return getTokens(userDto.getUser_id(), response);
	}

	// 카카오 로그인
	public OauthResponseDto loginWithGoogle(String accessToken, HttpServletResponse response) {
		GoogleOauthService googleOauthService = new GoogleOauthService(userService);
		UserDto userDto = googleOauthService.getUserProfileByToken(accessToken);

		return getTokens(userDto.getUser_id(), response);
	}

	// 액세스토큰, 리프레시토큰 생성
	public OauthResponseDto getTokens(Long user_id, HttpServletResponse response) {
		OauthResponseDto oauthResponseDto = new OauthResponseDto();

		final String accessToken = jwtTokenService.createAccessToken(user_id.toString());
		final String refreshToken = jwtTokenService.createRefreshToken(user_id.toString());

		UserDto userDto = userService.findByUserId(user_id);
		userDto.setRefreshToken(refreshToken);
		userService.updateRefreshToken(userDto);

		jwtTokenService.addRefreshTokenToCookie(refreshToken, response);

		oauthResponseDto.setAccessToken(accessToken);
		oauthResponseDto.setRefreshToken(refreshToken);

		return oauthResponseDto;
	}

	// 리프레시 토큰으로 액세스토큰 새로 갱신
	public String refreshAccessToken(String refreshToken) {
		UserDto userDto = userService.findByRefreshToken(refreshToken);
		if (userDto == null) {
			throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		if (!jwtTokenService.validateToken(refreshToken)) {
			throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		return jwtTokenService.createAccessToken(userDto.getUser_id().toString());
	}
}
