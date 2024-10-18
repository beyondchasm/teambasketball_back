package site.beyondchasm.teambasketball.player.command;

import lombok.Data;

import java.util.List;

@Data
public class PlayerFilterCommand {

    private int search_team_id;
    private List<String> search_position;
}
