package site.beyondchasm.teambasketball.notification;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.beyondchasm.teambasketball.notification.config.RabbitConfig;
import site.beyondchasm.teambasketball.notification.model.NotificationMessageDto;

/**
 * 알림 메시지를 RabbitMQ 큐로 발행하는 서비스 클래스.
 */
@Service
@Slf4j
public class NotificationProducer {

  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.queue.notification}")
  private String notificationQueue;

  /**
   * 생성자 주입을 통한 RabbitTemplate 의존성 주입.
   *
   * @param rabbitTemplate RabbitTemplate 인스턴스
   */
  public NotificationProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  /**
   * 알림 메시지를 RabbitMQ 큐로 발행합니다.
   *
   * @param message 알림 메시지 객체
   */
  public CompletableFuture<Void> sendNotificationAsync(NotificationMessageDto message) {
    return CompletableFuture.runAsync(() -> {
      rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATION_QUEUE, message);
    });
  }
}
