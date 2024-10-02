package site.beyondchasm.teambasketball.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.user.mapper.UserMapper;
import site.beyondchasm.teambasketball.user.model.UserDto;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserMapper userMapper;

	public void save(UserDto userDto) {
		userMapper.save(userDto);
	}

	public UserDto findByProviderId(String provider, String provider_id) {
		return userMapper.findByProviderId(provider, provider_id);
	}

	public UserDto findByUserId(Long user_id) {
		return userMapper.findByUserId(user_id);
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