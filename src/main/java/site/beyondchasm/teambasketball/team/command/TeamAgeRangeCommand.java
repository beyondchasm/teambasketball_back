package site.beyondchasm.teambasketball.team.command;

import lombok.Data;

@Data
public class TeamAgeRangeCommand {
    private long team_id;
    private String code_id;
}