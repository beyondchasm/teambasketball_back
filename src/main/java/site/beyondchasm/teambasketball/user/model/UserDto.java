package site.beyondchasm.teambasketball.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import site.beyondchasm.teambasketball.team.model.MyTeamDto;

@Data
public class UserDto {

  /**
   * 유저 ID
   */
  @Schema(description = "유저 ID")
  private Long userId;

  @Schema(description = "OAuth 제공자 (예: 구글, 카카오)")
  private String provider;

  @Schema(description = "OAuth 제공자의 유저 ID")
  private String providerId;

  @Schema(description = "프로필 이미지 URL")
  private String profileImage;

  @Schema(description = "사용자 이름")
  private String name;

  @Schema(description = "출생 연도")
  private String birthYear;

  @Schema(description = "성별")
  private String gender;

  @Schema(description = "역할 (예: 관리자, 사용자 등)")
  private String role;

  @Schema(description = "OAuth 리프레시 토큰")
  private String refreshToken;

  @Schema(description = "FCM 푸시 알림 토큰")
  private String fcmToken;

  @Schema(description = "이메일")
  private String email;

  @Schema(description = "체중 (kg)")
  private Integer weight;

  @Schema(description = "키 (cm)")
  private Integer height;

  @Schema(description = "포지션 코드")
  private String position;

  @Schema(description = "활동 지역 코드")
  private String regionCode;

  @Schema(description = "계정 생성 일자")
  private Date createdAt;

  @Schema(description = "계정 수정 일자")
  private Date updatedAt;

  @Schema(description = "사용자가 소속된 팀 정보")
  private MyTeamDto myTeam;
}
