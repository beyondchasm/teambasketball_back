package site.beyondchasm.teambasketball.team.command;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class TeamCreateCommand {

  private long teamId;
  private String teamName;
  private String teamLogoImage;
  private Date sinceDate;
  private List<String> ageRanges;
  private List<String> actDays;
  private List<String> actTimes;
  private long homeCourtId;
  private String regionCode;
  private String introduce;
  private String recruitment;
  private Boolean isRecruiting;
  private String snsAddress;
}
