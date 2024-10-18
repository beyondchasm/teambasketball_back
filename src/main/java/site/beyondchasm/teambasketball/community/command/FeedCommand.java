package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class FeedCommand {
    private Boolean is_team;
    private Long channel_id;
    private long team_id;
    private long feed_id;
    private String title;
    private String content_type;
    private String content;
}
