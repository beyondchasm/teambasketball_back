package site.beyondchasm.teambasketball.team.command;

import lombok.Data;
import site.beyondchasm.teambasketball.team.model.TeamDto;

import java.util.Date;
import java.util.List;

@Data
public class TeamCreateCommand {
    private long team_id;
    private String team_name;
    private String team_logo_image;
    private Date since_date;
    private List<String> age_ranges;
    private List<String> act_days;
    private List<String> act_times;
    private long home_court_id;
    private String region_code;
    private String introduce;
    private String recruitment;
    private Boolean is_recruiting;
    private String sns_address;

}