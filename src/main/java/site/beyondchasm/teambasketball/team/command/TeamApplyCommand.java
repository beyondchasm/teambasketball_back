package site.beyondchasm.teambasketball.team.command;

import lombok.Data;

@Data
public class TeamApplyCommand {
    private long team_id;
    private String introduction;
}