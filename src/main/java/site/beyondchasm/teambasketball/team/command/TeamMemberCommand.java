package site.beyondchasm.teambasketball.team.command;

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
public class TeamMemberCommand {
	private long team_id;
	private long user_id;
	private int player_number;
	private String role;
}