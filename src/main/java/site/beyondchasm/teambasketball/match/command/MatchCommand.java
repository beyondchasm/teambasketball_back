package site.beyondchasm.teambasketball.match.command;

import lombok.Data;

import java.util.Date;

@Data
public class MatchCommand {
    private long match_id;
    private String match_logo_image;
    private Date since_date;
    private String division_code;
    private String region_code;
    private String introduce;
}
