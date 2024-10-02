package site.beyondchasm.teambasketball.auth.service;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.auth.domain.GoogleInfoDto;
import site.beyondchasm.teambasketball.auth.enums.Provider;
import site.beyondchasm.teambasketball.user.model.UserDto;
import site.beyondchasm.teambasketball.user.service.UserService;

@RequiredArgsConstructor
@Service
public class GoogleOauthService {
	private final UserService userService;

	// 구글 API 호출해서 AccessToken으로 유저 정보 가져오기
	public Map<String, Object> getUserAttributesByToken(String accessToken) {
		return WebClient.create().get().uri("https://www.googleapis.com/oauth2/v2/userinfo")
				.headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken)).retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				}).block();
	}

	// 구글 API에서 가져온 유저 정보를 DB에 저장
	public UserDto getUserProfileByToken(String accessToken) {
		Map<String, Object> userAttributesByToken = getUserAttributesByToken(accessToken);
		GoogleInfoDto googleInfoDto = new GoogleInfoDto(userAttributesByToken);

		UserDto userDto = userService.findByProviderId(Provider.GOOGLE.getProvider(), googleInfoDto.getId().toString());

		UserDto newUserDto = UserDto.builder().provider_id(googleInfoDto.getId().toString())
				.email(googleInfoDto.getEmail()).provider(Provider.GOOGLE.getProvider()).name(googleInfoDto.getName())
				.profile_image(googleInfoDto.getProfileImageUrl()).build();

		if (userDto != null) {
//			userService.update(userDto);
		} else {
			userService.save(newUserDto);
		}

		userDto = userService.findByProviderId(Provider.GOOGLE.getProvider(), googleInfoDto.getId().toString());
		return userDto;
	}
}
