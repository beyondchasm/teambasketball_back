package site.beyondchasm.teambasketball.team.command;

import lombok.Data;
import site.beyondchasm.teambasketball.auth.enums.TeamMemberRole;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TeamMemberCommand {
    private long team_id;
    private long user_id;
    private int player_number;
    private String role;
    private String introduction;
    private Date applicated_at;
    private Date joined_at;

}