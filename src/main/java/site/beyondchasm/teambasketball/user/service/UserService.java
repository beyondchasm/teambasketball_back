package site.beyondchasm.teambasketball.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.team.mapper.TeamMapper;
import site.beyondchasm.teambasketball.team.model.MyTeamDto;
import site.beyondchasm.teambasketball.team.model.TeamDto;
import site.beyondchasm.teambasketball.team.model.TeamMemberDto;
import site.beyondchasm.teambasketball.user.mapper.UserMapper;
import site.beyondchasm.teambasketball.user.model.UserDto;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;
    private final TeamMapper teamMapper;

    public void save(UserDto userDto) {
        userMapper.save(userDto);
    }

    public UserDto findByProviderId(String provider, String provider_id) {
        return userMapper.findByProviderId(provider, provider_id);
    }

    public UserDto findByUserId(Long user_id) {
        UserDto userDto = userMapper.findByUserId(user_id);
        if (userDto != null) {

            MyTeamDto myTeamDto = teamMapper.getMyTeamDetail(user_id);
            if (myTeamDto != null) {
                userDto.setMyTeam(myTeamDto);
                TeamMemberDto myMembrInfo = teamMapper.getMyMembrInfo(user_id);
                if (myMembrInfo != null) {
                    userDto.getMyTeam().setMember(myMembrInfo);
                }
            }
        }
        return userDto;
    }

    public UserDto findByRefreshToken(String refreshToken) {
        return userMapper.findByRefreshToken(refreshToken);
    }

    public void update(UserDto userDto) {
        userMapper.update(userDto);
    }

    public void updateRefreshToken(UserDto userDto) {
        userMapper.updateRefreshToken(userDto);
    }

}