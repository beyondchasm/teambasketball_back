package site.beyondchasm.teambasketball.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import site.beyondchasm.teambasketball.user.model.UserDto;

@Mapper
public interface UserMapper {
	int save(UserDto userDto);

	UserDto findByProviderId(String provider, String provider_id);

	UserDto findByUserId(Long user_id);

	UserDto findByRefreshToken(String refreshToken);

	int update(UserDto userDto);

	void updateRefreshToken(UserDto userDto);

}