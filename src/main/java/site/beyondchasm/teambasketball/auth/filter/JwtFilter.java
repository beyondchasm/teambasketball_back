package site.beyondchasm.teambasketball.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.auth.model.UserPrincipal;
import site.beyondchasm.teambasketball.auth.service.JwtTokenService;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.user.model.UserDto;
import site.beyondchasm.teambasketball.user.service.UserService;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  private final JwtTokenService jwtTokenService;
  private final UserService userService;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    logger.info("[JwtFilter] : " + httpServletRequest.getRequestURL().toString());
    String jwt = resolveToken(httpServletRequest);

    try {
      if (StringUtils.hasText(jwt) && jwtTokenService.validateToken(jwt)) {
        authenticate(jwt);
      } else {
        handleInvalidToken(httpServletRequest, httpServletResponse);
      }
    } catch (CustomException e) {
      handleInvalidToken(httpServletRequest, httpServletResponse);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private void authenticate(String jwt) {
    Long userId = Long.valueOf(jwtTokenService.getPayload(jwt));
    UserDto user = userService.findByUserId(userId);
    if (user == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    UserDetails userDetails = UserPrincipal.create(user);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null,
        userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String refreshToken = jwtTokenService.getRefreshTokenFromCookie(request);
    if (StringUtils.hasText(refreshToken) && jwtTokenService.validateToken(refreshToken)) {
      Long userId = Long.valueOf(jwtTokenService.getPayload(refreshToken));
      UserDto user = userService.findByUserId(userId);
      if (user == null) {
        throw new CustomException(ErrorCode.NOT_EXIST_USER);
      }

      // 새로운 액세스 토큰 발행
      String newAccessToken = jwtTokenService.createAccessToken(String.valueOf(userId));
      response.setHeader(AUTHORIZATION_HEADER, "Bearer " + newAccessToken);

      // 새로운 액세스 토큰으로 인증 설정
      authenticate(newAccessToken);
    } else {
      throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
    }
  }
}
