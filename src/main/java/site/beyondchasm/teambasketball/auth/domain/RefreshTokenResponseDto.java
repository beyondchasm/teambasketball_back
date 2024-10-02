package site.beyondchasm.teambasketball.auth.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponseDto {
	private String accessToken;
}