package site.beyondchasm.teambasketball.team.command;

import java.util.List;

import lombok.Data;

@Data
public class TeamFilterCommand {

    private List<String> search_age_range_code;
    private List<String> search_act_day_code;
    private List<String> search_act_time_code;
    private String search_region_code;
    private String search_team_name;
    private String search_order;

}
