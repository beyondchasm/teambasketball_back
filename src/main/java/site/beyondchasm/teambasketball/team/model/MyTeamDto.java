package site.beyondchasm.teambasketball.team.model;

import lombok.Data;

@Data
public class MyTeamDto {

  private long teamId;
  private String teamName;
  private String teamLogoImage;
  private TeamMemberDto member;
}
