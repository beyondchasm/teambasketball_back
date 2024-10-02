package site.beyondchasm.teambasketball.user.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	private Long user_id; // 유저 ID
	private String provider; // OAuth 제공자 (예: 구글, 카카오)
	private String provider_id; // 제공자 ID
	private String profile_image; // 프로필 이미지 URL
	private String name; // 사용자 이름
	private String birthyear; // 출생 연도
	private String gender; // 성별
	private String role; // 역할 (예: 관리자, 사용자 등)
	private String refreshToken; // OAuth 리프레시 토큰
	private String fcmToken; // FCM 푸시 알림 토큰
	private String email; // 이메일
	private Integer weight; // 체중 (kg)
	private Integer height; // 키 (cm)
	private Date createdAt; // 생성 일자
	private Date updatedAt; // 수정 일자
}
