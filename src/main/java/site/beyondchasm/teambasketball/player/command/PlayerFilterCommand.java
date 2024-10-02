package site.beyondchasm.teambasketball.player.command;

import lombok.Data;

@Data
public class PlayerFilterCommand {

    private String search_team_id;
    private String search_region_code;
    private String[] search_position;
}
