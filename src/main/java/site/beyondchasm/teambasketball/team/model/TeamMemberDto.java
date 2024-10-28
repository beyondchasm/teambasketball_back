package site.beyondchasm.teambasketball.team.model;

import lombok.Data;
import site.beyondchasm.teambasketball.player.model.PlayerDto;
import java.util.Date;

@Data
public class TeamMemberDto {

  private long teamId;
  private PlayerDto player;
  private int playerNumber;
  private String role;
  private String applicationContent;
  private Date joinedAt;
  private Date applicatedAt;
}
