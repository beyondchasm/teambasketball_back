package site.beyondchasm.teambasketball.team.command;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class TeamUpdateCommand {

  private long teamId;
  private String teamName;
  private String teamLogoImage;
  private Date sinceDate;
  private List<String> ageRanges;
  private List<String> actDays;
  private List<String> actTimes;
  private long homeCourtId;
  private String recruitment;
  private Boolean isRecruiting;
  private String regionCode;
  private String introduce;
  private String snsAddress;
}
