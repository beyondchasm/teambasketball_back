package site.beyondchasm.teambasketball.court.command;

import java.util.List;
import lombok.Data;

@Data
public class CourtFilterCommand {

  private List<String> searchCourtType;
  private String searchCourtName;
  private String searchRegionCode;
}
