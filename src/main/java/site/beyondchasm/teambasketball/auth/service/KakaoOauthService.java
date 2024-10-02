package site.beyondchasm.teambasketball.auth.service;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.auth.domain.KakaoInfoDto;
import site.beyondchasm.teambasketball.auth.enums.Provider;
import site.beyondchasm.teambasketball.user.model.UserDto;
import site.beyondchasm.teambasketball.user.service.UserService;

@RequiredArgsConstructor
@Service
public class KakaoOauthService {
	private final UserService userService;

	// 카카오Api 호출해서 AccessToken으로 유저정보 가져오기
	public Map<String, Object> getUserAttributesByToken(String accessToken) {
		return WebClient.create().get().uri("https://kapi.kakao.com/v2/user/me")
				.headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken)).retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				}).block();
	}

	// 카카오API에서 가져온 유저정보를 DB에 저장
	public UserDto getUserProfileByToken(String accessToken) {
		Map<String, Object> userAttributesByToken = getUserAttributesByToken(accessToken);
		KakaoInfoDto kakaoInfoDto = new KakaoInfoDto(userAttributesByToken);

		UserDto userDto = userService.findByProviderId(Provider.KAKAO.getProvider(), kakaoInfoDto.getId().toString());

		UserDto newUserDto = UserDto.builder().provider_id(kakaoInfoDto.getId().toString())
				.email(kakaoInfoDto.getEmail()).provider(Provider.KAKAO.getProvider()).name(kakaoInfoDto.getName())
				.profile_image(kakaoInfoDto.getProfileImageUrl()).birthyear(kakaoInfoDto.getBirthyear())
				.gender(kakaoInfoDto.getGender()).build();

		if (userDto != null) {
//			userService.update(userDto);
		} else {
			userService.save(newUserDto);
		}

		userDto = userService.findByProviderId(Provider.KAKAO.getProvider(), kakaoInfoDto.getId().toString());
		return userDto;
	}
}
