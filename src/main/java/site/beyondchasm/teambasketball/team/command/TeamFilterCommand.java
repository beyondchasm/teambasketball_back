package site.beyondchasm.teambasketball.team.command;

import java.util.List;
import lombok.Data;

@Data
public class TeamFilterCommand {

  private List<String> searchAgeRangeCode;
  private List<String> searchActDayCode;
  private List<String> searchActTimeCode;
  private String searchRegionCode;
  private String searchTeamName;
  private String searchOrder;
}
