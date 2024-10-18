package site.beyondchasm.teambasketball.team.model;

import java.util.Date;
import java.util.List;

import lombok.*;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.court.model.CourtDto;

@Data
public class TeamDto {
    private long team_id;
    private String team_name;
    private String team_logo_image;
    private Date since_date;
    private CourtDto home_court;
    private List<CodeDTO> age_ranges;
    private List<CodeDTO> act_days;
    private List<CodeDTO> act_times;
    private List<TeamMemberDto> members;
    private String region_code;
    private String introduce;
    private String recruitment;
    private Boolean is_recruiting;
    private String sns_address;
    private Date created_at;
    private Date updated_at;
}