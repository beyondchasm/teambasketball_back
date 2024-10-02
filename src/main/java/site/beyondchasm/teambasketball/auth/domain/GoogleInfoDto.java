package site.beyondchasm.teambasketball.auth.domain;

import java.util.Map;

import lombok.Getter;

@Getter
public class GoogleInfoDto {
	private String id;
	private String email;
	private String name;
	private String profileImageUrl;

	public GoogleInfoDto(Map<String, Object> attributes) {
		this.id = (String) attributes.get("id");
		this.email = (String) attributes.get("email");
		this.name = (String) attributes.get("name");
		this.profileImageUrl = (String) attributes.get("picture");
	}
}
