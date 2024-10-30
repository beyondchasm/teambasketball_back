package site.beyondchasm.teambasketball.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 알림 메시지 데이터를 담는 DTO 클래스.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessageDto implements Serializable {

  private String token;   // 사용자 FCM 토큰
  private String title;   // 알림 제목
  private String body;    // 알림 본문

}
