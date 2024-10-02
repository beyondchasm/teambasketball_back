package site.beyondchasm.teambasketball.auth.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OauthResponseDto {
	private String accessToken;
	private String refreshToken;
}