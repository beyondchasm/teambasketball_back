package site.beyondchasm.teambasketball.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;

@Service
public class JwtTokenService implements InitializingBean {
	private long accessTokenExpirationInSeconds;
	private long refreshTokenExpirationInSeconds;
	private final String secretKey;
	private static Key key;

	public JwtTokenService(@Value("${jwt.access.token.expiration.seconds}") long accessTokenExpirationInSeconds,
			@Value("${jwt.refresh.token.expiration.seconds}") long refreshTokenExpirationInSeconds,
			@Value("${jwt.token.secret-key}") String secretKey) {
		this.accessTokenExpirationInSeconds = accessTokenExpirationInSeconds * 1000;
		this.refreshTokenExpirationInSeconds = refreshTokenExpirationInSeconds * 1000;
		this.secretKey = secretKey;
	}

	@Override
	public void afterPropertiesSet() {
		this.key = getKeyFromBase64EncodedKey(encodeBase64SecretKey(secretKey));
	}

	public String createAccessToken(String payload) {
		return createToken(payload, accessTokenExpirationInSeconds);
	}

	public String createRefreshToken(String payload) {
		return createToken(payload, refreshTokenExpirationInSeconds);
	}

	public String createToken(String payload, long expireLength) {
		Claims claims = Jwts.claims().setSubject(payload);
		Date now = new Date();
		Date validity = new Date(now.getTime() + expireLength);
		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256).compact();
	}

	public String getPayload(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
		} catch (ExpiredJwtException e) {
			return e.getClaims().getSubject();
		} catch (JwtException e) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return !claimsJws.getBody().getExpiration().before(new Date());
		} catch (JwtException | IllegalArgumentException exception) {
			return false;
		}
	}

	private String encodeBase64SecretKey(String secretKey) {
		return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
		Key key = Keys.hmacShaKeyFor(keyBytes);
		return key;
	}

	// 클라이언트 쿠키에 리프레시 토큰 저장 시켜주는 메소드
	public void addRefreshTokenToCookie(String refreshToken, HttpServletResponse response) {
		Long age = refreshTokenExpirationInSeconds;
		Cookie cookie = new Cookie("refresh_token", refreshToken);
		cookie.setPath("/");
		cookie.setMaxAge(age.intValue());
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	// 쿠키에서 리프레시 토큰을 가져오는 메소드
	public String getRefreshTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refresh_token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
