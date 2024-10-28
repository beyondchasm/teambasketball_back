package site.beyondchasm.teambasketball.team.model;

import java.util.Date;
import java.util.List;
import lombok.Data;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.court.model.CourtDto;

@Data
public class TeamDto {

  private long teamId;
  private String teamName;
  private String teamLogoImage;
  private Date sinceDate;
  private CourtDto homeCourt;
  private List<CodeDTO> ageRanges;
  private List<CodeDTO> actDays;
  private List<CodeDTO> actTimes;
  private List<TeamMemberDto> members;
  private String regionCode;
  private String introduce;
  private String recruitment;
  private Boolean isRecruiting;
  private String snsAddress;
  private Date createdAt;
  private Date updatedAt;
}
