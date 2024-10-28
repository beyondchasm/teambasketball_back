package site.beyondchasm.teambasketball.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import site.beyondchasm.teambasketball.user.model.UserDto;

@Mapper
public interface UserMapper {

  /**
   * 새로운 사용자 정보를 저장합니다.
   *
   * @param userDto 저장할 사용자 정보
   * @return 저장 성공 여부 (1: 성공, 0: 실패)
   */
  int save(UserDto userDto);

  /**
   * 주어진 provider와 providerId로 사용자 정보를 조회합니다.
   *
   * @param provider   OAuth 제공자 (예: 구글, 카카오)
   * @param providerId 제공자 ID
   * @return 조회된 사용자 정보
   */
  UserDto findByProviderId(String provider, String providerId);

  /**
   * 주어진 사용자 ID로 사용자 정보를 조회합니다.
   *
   * @param userId 사용자 ID
   * @return 조회된 사용자 정보
   */
  UserDto findByUserId(Long userId);

  /**
   * 주어진 리프레시 토큰으로 사용자 정보를 조회합니다.
   *
   * @param refreshToken OAuth 리프레시 토큰
   * @return 조회된 사용자 정보
   */
  UserDto findByRefreshToken(String refreshToken);

  /**
   * 사용자 정보를 업데이트합니다.
   *
   * @param userDto 업데이트할 사용자 정보
   * @return 업데이트 성공 여부 (1: 성공, 0: 실패)
   */
  int update(UserDto userDto);

  /**
   * 주어진 사용자 ID로 리프레시 토큰을 업데이트합니다.
   *
   * @param userDto 업데이트할 리프레시 토큰과 사용자 ID를 포함한 정보
   */
  void updateRefreshToken(UserDto userDto);
}
