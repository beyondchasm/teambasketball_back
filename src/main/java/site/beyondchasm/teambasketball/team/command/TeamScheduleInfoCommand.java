package site.beyondchasm.teambasketball.team.command;

import java.time.LocalTime;
import lombok.Data;

@Data
public class TeamScheduleInfoCommand {

  private Long teamId;
  private Long courtId;
  private String dayOfWeek;
  private LocalTime startTime;
  private LocalTime endTime;
}
