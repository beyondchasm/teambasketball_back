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

  /**
   * 카카오 API를 호출하여 Access Token을 사용해 유저 정보를 가져옵니다.
   *
   * @param accessToken 카카오 인증 Access Token
   * @return 카카오에서 가져온 유저 정보의 Map 객체
   */
  public Map<String, Object> getUserAttributesByToken(String accessToken) {
    return WebClient.create()
        .get()
        .uri("https://kapi.kakao.com/v2/user/me")
        .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
        })
        .block();
  }

  /**
   * 카카오 API에서 가져온 유저 정보를 바탕으로 유저 정보를 데이터베이스에 저장하거나 업데이트합니다.
   *
   * @param accessToken 카카오 인증 Access Token
   * @return 데이터베이스에 저장된 유저 정보
   */
  public UserDto getUserProfileByToken(String accessToken) {
    Map<String, Object> userAttributesByToken = getUserAttributesByToken(accessToken);
    KakaoInfoDto kakaoInfoDto = new KakaoInfoDto(userAttributesByToken);

    // DB에서 providerId로 유저 조회
    UserDto userDto = userService.findByProviderId(Provider.KAKAO.getProvider(),
        kakaoInfoDto.getId().toString());

    // 새로운 유저 정보 생성
    UserDto newUserDto = new UserDto();
    newUserDto.setProviderId(kakaoInfoDto.getId().toString());
    newUserDto.setEmail(kakaoInfoDto.getEmail());
    newUserDto.setProvider(Provider.KAKAO.getProvider());
    newUserDto.setName(kakaoInfoDto.getName());
    newUserDto.setProfileImage(kakaoInfoDto.getProfileImageUrl());
    newUserDto.setBirthYear(kakaoInfoDto.getBirthyear());
    newUserDto.setGender(kakaoInfoDto.getGender());

    // 유저 정보가 이미 존재하면 업데이트, 없으면 새로 저장
    if (userDto != null) {
      // 필요 시 주석 해제하여 업데이트 기능 사용 가능
      // userService.update(userDto);
    } else {
      userService.save(newUserDto);
    }

    // 저장된 유저 정보 재조회 후 반환
    userDto = userService.findByProviderId(Provider.KAKAO.getProvider(),
        kakaoInfoDto.getId().toString());
    return userDto;
  }
}
