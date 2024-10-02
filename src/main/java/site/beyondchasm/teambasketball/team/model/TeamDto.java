package site.beyondchasm.teambasketball.team.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TeamDto {
	private long team_id;
	private String team_name;
	private String team_logo_image;
	private Date since_date;
	private String division_code;
	private String region_code;
	private String introduce;
	private Date created_at;
	private Date updated_at;
}