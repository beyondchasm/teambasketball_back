package site.beyondchasm.teambasketball.team.model;

import java.time.LocalTime;
import lombok.Data;

@Data
public class TeamScheduleInfoDto {

  private Long teamId;
  private Long seq;
  private Long courtId;
  private String courtName;
  private String dayOfWeek;
  private LocalTime startTime;
  private LocalTime endTime;
}
