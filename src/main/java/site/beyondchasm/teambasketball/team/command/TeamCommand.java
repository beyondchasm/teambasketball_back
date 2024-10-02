package site.beyondchasm.teambasketball.team.command;

import java.util.Date;

import lombok.Data;

@Data
public class TeamCommand {
	private long team_id;
	private String team_name;
	private String team_logo_image;
	private Date  since_date;
	private String division_code;
	private String region_code;
	private String introduce;
}
