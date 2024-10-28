package site.beyondchasm.teambasketball.player.model;

import lombok.Data;

@Data
public class PlayerDto {

  private Long userId; // 유저 ID
  private String profileImage; // 프로필 이미지 URL
  private String name; // 사용자 이름
  private String birthYear; // 출생 연도
  private String gender; // 성별
  private String email; // 이메일
  private Integer weight; // 체중 (kg)
  private Integer height; // 키 (cm)
  private String position; // 포지션 코드
  private String regionCode; // 활동 지역
}
