package site.beyondchasm.teambasketball.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.auth.utils.SecurityUtil;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.user.model.UserDto;
import site.beyondchasm.teambasketball.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	// 유저정보 조회 API
	@GetMapping("/me")
	public UserDto me() {
		final Long user_id = SecurityUtil.getCurrentUserId();
		UserDto userDto = userService.findByUserId(user_id);
		if (userDto == null) {
			throw new CustomException(ErrorCode.NOT_EXIST_USER);
		}
		return userDto;
	}

	// 유저 프로필 수정 API
	@PutMapping("/editProfile")
	public UserDto update(@RequestBody UserDto userModel) {
		final Long user_id = SecurityUtil.getCurrentUserId();
		try {
			userService.update(userModel);
		} catch (Exception e) {
			// TODO: handle exception
		}
		UserDto userDto = userService.findByUserId(user_id);
		if (userModel == null) {
			throw new CustomException(ErrorCode.NOT_EXIST_USER);
		}
		return userDto;
	}
}
