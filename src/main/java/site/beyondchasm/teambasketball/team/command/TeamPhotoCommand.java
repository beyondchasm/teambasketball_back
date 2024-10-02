package site.beyondchasm.teambasketball.team.command;

import java.util.List;

import lombok.Data;

@Data
public class TeamPhotoCommand {
	private long team_id;
	private List<String> image_path;
}
