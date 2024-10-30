package site.beyondchasm.teambasketball.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.beyondchasm.teambasketball.notification.NotificationProducer;
import site.beyondchasm.teambasketball.notification.model.NotificationMessageDto;

/**
 * 알림 전송 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

  private final NotificationProducer notificationProducer;

  /**
   * 알림 메시지를 전송하는 메서드.
   *
   * @param request 알림 요청 데이터
   */
  public void sendNotification(NotificationMessageDto request) {
    log.info("Processing notification request: {}", request);
    NotificationMessageDto message = new NotificationMessageDto(request.getToken(),
        request.getTitle(), request.getBody());
    notificationProducer.sendNotificationAsync(message);
  }
}
