package site.beyondchasm.teambasketball.match.command;

import lombok.Data;
import site.beyondchasm.teambasketball.auth.enums.MatchMemberStatus;

import java.util.Date;

@Data
public class MatchMemberCommand {

    private long match_id;
    private long user_id;
    private String status;
}