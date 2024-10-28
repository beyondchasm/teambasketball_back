package site.beyondchasm.teambasketball.team.command;

import lombok.Data;

@Data
public class TeamAgeRangeCommand {

  private long teamId;
  private String codeId;
}