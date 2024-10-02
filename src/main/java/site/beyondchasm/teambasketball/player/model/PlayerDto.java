package site.beyondchasm.teambasketball.player.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlayerDto {
    private Long user_id; // 유저 ID
    private String profile_image; // 프로필 이미지 URL
    private String name; // 사용자 이름
    private String birthyear; // 출생 연도
    private String gender; // 성별
    private String email; // 이메일
    private Integer weight; // 체중 (kg)
    private Integer height; // 키 (cm)
}