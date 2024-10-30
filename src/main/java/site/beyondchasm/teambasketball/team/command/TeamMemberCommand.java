package site.beyondchasm.teambasketball.team.command;

import java.util.Date;
import lombok.Data;

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
