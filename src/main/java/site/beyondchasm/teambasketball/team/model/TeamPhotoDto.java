package site.beyondchasm.teambasketball.team.model;

import lombok.Data;

@Data
public class TeamPhotoDto {
	private Long team_id;
	private Long seq;
	private String image_path;
}