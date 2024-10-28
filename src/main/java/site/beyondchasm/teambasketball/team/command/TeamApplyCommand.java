package site.beyondchasm.teambasketball.team.command;

import lombok.Data;

@Data
public class TeamApplyCommand {

  private long teamId;
  private String introduction;
}