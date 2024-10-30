package site.beyondchasm.teambasketball.match.command;

import lombok.Data;

@Data
public class MatchMemberCommand {

  private long matchId;
  private long userId;
  private String status;
}