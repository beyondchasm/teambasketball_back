package site.beyondchasm.teambasketball.team.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.beyondchasm.teambasketball.user.model.UserDto;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TeamMemberDto {
	private long team_id;
	private long user_id;
	private int player_number;
	private String role;
	private UserDto player;

}