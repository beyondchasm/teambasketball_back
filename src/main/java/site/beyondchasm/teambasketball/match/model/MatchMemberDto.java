package site.beyondchasm.teambasketball.match.model;

import lombok.Data;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

import java.util.Date;

@Data
public class MatchMemberDto {
    private long match_id;
    private PlayerDto player;
    private String status;
    private Date joined_at;
}