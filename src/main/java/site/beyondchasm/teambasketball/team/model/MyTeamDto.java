package site.beyondchasm.teambasketball.team.model;

import lombok.Data;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.court.model.CourtDto;

import java.util.Date;
import java.util.List;

@Data
public class MyTeamDto {
    private long team_id;
    private String team_name;
    private String team_logo_image;
    private TeamMemberDto member;
}