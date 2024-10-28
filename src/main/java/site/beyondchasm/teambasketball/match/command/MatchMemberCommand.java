package site.beyondchasm.teambasketball.match.command;

import lombok.Data;
import site.beyondchasm.teambasketball.auth.enums.MatchMemberStatus;

import java.util.Date;

@Data
public class MatchMemberCommand {

  private long matchId;
  private long userId;
  private String status;
}