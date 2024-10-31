package site.beyondchasm.teambasketball;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeambasketballApplication {

  public static void main(String[] args) {
    // .env 파일 로드
    Dotenv dotenv = Dotenv.load();

    // .env의 모든 변수를 시스템 프로퍼티로 설정
    dotenv.entries().forEach(entry -> {
      System.setProperty(entry.getKey(), entry.getValue());
    });
    System.out.println("AWS_ACCESS_KEY_ID: " + System.getProperty("AWS_ACCESS_KEY_ID"));
    System.out.println("AWS_SECRET_ACCESS_KEY: " + System.getProperty("AWS_SECRET_ACCESS_KEY"));

    SpringApplication.run(TeambasketballApplication.class, args);
  }

}
