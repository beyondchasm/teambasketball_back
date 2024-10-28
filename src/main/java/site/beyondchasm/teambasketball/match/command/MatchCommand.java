package site.beyondchasm.teambasketball.match.command;

import lombok.Data;
import java.util.Date;

@Data
public class MatchCommand {

  private long matchId;
  private String matchLogoImage;
  private Date sinceDate;
  private String divisionCode;
  private String regionCode;
  private String introduce;
}
