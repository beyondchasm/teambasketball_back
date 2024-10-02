package site.beyondchasm.teambasketball.team.command;

import java.util.List;

import lombok.Data;

@Data
public class TeamFilterCommand {
	private List<String> search_division_code;
	private String search_region_code;
	private String search_team_name;
}
