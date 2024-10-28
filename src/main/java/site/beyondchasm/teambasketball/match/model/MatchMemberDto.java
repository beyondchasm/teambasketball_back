package site.beyondchasm.teambasketball.match.model;

import lombok.Data;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

import java.util.Date;

@Data
public class MatchMemberDto {

  private long matchId;
  private PlayerDto player;
  private String status;
  private Date joinedAt;
}