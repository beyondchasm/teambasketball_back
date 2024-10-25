package site.beyondchasm.teambasketball.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.auth.utils.SecurityUtil;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.user.model.UserDto;
import site.beyondchasm.teambasketball.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  /**
   * 현재 유저 정보 조회 API GET /api/users/me
   */
  @GetMapping("/me")
  public ResponseEntity<UserDto> getCurrentUser() {
    Long userId = SecurityUtil.getCurrentUserId();
    UserDto userDto = userService.findByUserId(userId);

    if (userDto == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(userDto);
  }

  /**
   * 유저 프로필 수정 API PUT /api/users/me
   */
  @PutMapping("/me")
  public ResponseEntity<UserDto> updateUserProfile(@RequestBody UserDto userModel) {
    Long userId = SecurityUtil.getCurrentUserId();

    try {
      userService.update(userModel);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.UPDATE_FAILED);
    }

    UserDto updatedUserDto = userService.findByUserId(userId);
    if (updatedUserDto == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(updatedUserDto);
  }
}
