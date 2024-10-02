package site.beyondchasm.teambasketball.auth.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequiredArgsConstructor
public class OAuthController {
    private final OauthService oauthService;

    @PostMapping("/login/oauth/{provider}")
    public OauthResponseDto login(@PathVariable String provider, @RequestBody OauthRequestDto oauthRequestDto,
                                  HttpServletResponse response, HttpServletRequest request) {
        OauthResponseDto oauthResponseDto = switch (provider) {
            case "kakao" -> oauthService.loginWithKakao(oauthRequestDto.getAccessToken(), response);
            case "google" -> oauthService.loginWithGoogle(oauthRequestDto.getAccessToken(), response);
            default -> throw new IllegalArgumentException("Invalid provider: " + provider);
        };

        return oauthResponseDto;
    }

    // 리프레시 토큰으로 액세스토큰 재발 ≤급 받는 로직
    @PostMapping("/token/refresh")
    public RefreshTokenResponseDto tokenRefresh(HttpServletRequest request) {
        RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();
        Cookie[] list = request.getCookies();
        if (list == null) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Cookie refreshTokenCookie = Arrays.stream(list).filter(cookie -> cookie.getName().equals("refresh_token"))
                .collect(Collectors.toList()).get(0);

        if (refreshTokenCookie == null) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String accessToken = oauthService.refreshAccessToken(refreshTokenCookie.getValue());
        refreshTokenResponseDto.setAccessToken(accessToken);
        return refreshTokenResponseDto;
    }
}
