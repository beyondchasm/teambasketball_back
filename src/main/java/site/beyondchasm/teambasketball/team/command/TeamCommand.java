package site.beyondchasm.teambasketball.team.command;

import java.util.Date;
import lombok.Data;

@Data
public class TeamCommand {

  private long teamId;
  private String teamName;
  private String teamLogoImage;
  private Date sinceDate;
  private String divisionCode;
  private String regionCode;
  private String introduce;
}
