package site.beyondchasm.teambasketball.notification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 설정을 담당하는 구성 클래스.
 */
@Configuration
public class RabbitConfig {

  // 알림 큐 이름 정의
  public static final String NOTIFICATION_QUEUE = "notification.queue";

  /**
   * 알림 큐를 생성하는 Bean.
   *
   * @return 선언된 알림 큐
   */
  @Bean
  public Queue notificationQueue() {
    return new Queue(NOTIFICATION_QUEUE, true); // 내구성 있는 큐
  }

  /**
   * RabbitTemplate Bean을 설정하여 메시지 전송 시 사용.
   *
   * @param connectionFactory RabbitMQ 연결 공장
   * @return 설정된 RabbitTemplate 인스턴스
   */
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    // Jackson 메시지 변환기 설정
    template.setMessageConverter(jackson2JsonMessageConverter());
    return template;
  }

  /**
   * Jackson 기반의 JSON 메시지 변환기를 설정하는 Bean.
   *
   * @return Jackson2JsonMessageConverter 인스턴스
   */
  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
