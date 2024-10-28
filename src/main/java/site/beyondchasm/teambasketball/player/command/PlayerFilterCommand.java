package site.beyondchasm.teambasketball.player.command;

import lombok.Data;

import java.util.List;

@Data
public class PlayerFilterCommand {

  private int searchTeamId;
  private List<String> searchPosition;
}
