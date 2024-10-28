package site.beyondchasm.teambasketball.team.model;

import lombok.Data;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.court.model.CourtDto;

@Data
public class MyTeamDto {

  private long teamId;
  private String teamName;
  private String teamLogoImage;
  private TeamMemberDto member;
}
