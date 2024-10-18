package site.beyondchasm.teambasketball.team.model;

import lombok.*;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

import java.util.Date;

@Data
public class TeamMemberDto {
    private long team_id;
    private PlayerDto player;
    private int player_number;
    private String role;
    private String application_content;
    private Date joined_at;
    private Date applicated_at;
}