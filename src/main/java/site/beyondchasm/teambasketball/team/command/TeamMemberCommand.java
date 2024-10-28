package site.beyondchasm.teambasketball.team.command;

import lombok.Data;
import site.beyondchasm.teambasketball.auth.enums.TeamMemberRole;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TeamMemberCommand {

  private long teamId;
  private long userId;
  private int playerNumber;
  private String role;
  private String introduction;
  private Date applicatedAt;
  private Date joinedAt;
}
