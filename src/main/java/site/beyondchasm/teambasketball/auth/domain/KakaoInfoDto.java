package site.beyondchasm.teambasketball.auth.domain;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoInfoDto {
	private Long id;
	private String email;
	private String profileImageUrl;
	private String name;
	private String birthyear;
	private String gender;

	public KakaoInfoDto(Map<String, Object> attributes) {
		this.id = Long.valueOf(attributes.get("id").toString());
		Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
		this.email = kakao_account.get("email") != null ? kakao_account.get("email").toString() : "";
		Map<String, Object> kakao_profile = (Map<String, Object>) kakao_account.get("profile");
		this.profileImageUrl = kakao_profile.get("profile_image_url") != null
				? kakao_profile.get("profile_image_url").toString()
				: "";
		this.name = kakao_account.get("name") != null ? kakao_account.get("name").toString() : "";
		this.birthyear = kakao_account.get("birthyear") != null ? kakao_account.get("birthyear").toString() : "";
		this.gender = kakao_account.get("gender") != null ? kakao_account.get("gender").toString() : "";
	}
}