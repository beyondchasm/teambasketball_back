package site.beyondchasm.teambasketball.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.beyondchasm.teambasketball.team.mapper.TeamMapper;
import site.beyondchasm.teambasketball.team.model.MyTeamDto;
import site.beyondchasm.teambasketball.team.model.TeamMemberDto;
import site.beyondchasm.teambasketball.user.mapper.UserMapper;
import site.beyondchasm.teambasketball.user.model.UserDto;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserMapper userMapper;
  private final TeamMapper teamMapper;

  /**
   * 새로운 사용자를 저장합니다.
   *
   * @param userDto 저장할 사용자 정보
   */
  public void save(UserDto userDto) {
    userMapper.save(userDto);
  }

  /**
   * OAuth 제공자와 제공자 ID로 사용자를 조회합니다.
   *
   * @param provider   OAuth 제공자
   * @param providerId 제공자 ID
   * @return 조회된 사용자 정보
   */
  public UserDto findByProviderId(String provider, String providerId) {
    return userMapper.findByProviderId(provider, providerId);
  }

  /**
   * 사용자 ID로 사용자의 상세 정보를 조회합니다. 사용자의 팀 정보 및 팀에서의 역할도 함께 조회하여 설정합니다.
   *
   * @param userId 사용자 ID
   * @return 조회된 사용자 상세 정보
   */
  public UserDto findByUserId(Long userId) {
    UserDto userDto = userMapper.findByUserId(userId);
    if (userDto != null) {
      MyTeamDto myTeamDto = teamMapper.getMyTeamDetail(userId);
      if (myTeamDto != null) {
        userDto.setMyTeam(myTeamDto);
        TeamMemberDto myMemberInfo = teamMapper.getMyMemberInfo(userId);
        if (myMemberInfo != null) {
          userDto.getMyTeam().setMember(myMemberInfo);
        }
      }
    }
    return userDto;
  }

  /**
   * 리프레시 토큰으로 사용자를 조회합니다.
   *
   * @param refreshToken 리프레시 토큰
   * @return 조회된 사용자 정보
   */
  public UserDto findByRefreshToken(String refreshToken) {
    return userMapper.findByRefreshToken(refreshToken);
  }

  /**
   * 사용자 정보를 업데이트합니다.
   *
   * @param userDto 업데이트할 사용자 정보
   */
  public void update(UserDto userDto) {
    userMapper.update(userDto);
  }

  /**
   * 사용자의 리프레시 토큰을 업데이트합니다.
   *
   * @param userDto 업데이트할 사용자 정보
   */
  public void updateRefreshToken(UserDto userDto) {
    userMapper.updateRefreshToken(userDto);
  }
}
