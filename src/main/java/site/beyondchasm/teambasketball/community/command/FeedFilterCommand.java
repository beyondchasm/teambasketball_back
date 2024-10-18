package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class FeedFilterCommand {
    private long logined_user_id;
    private String search_keyword;
    private Long channel_id;
    private Long team_id;
    private String sort_by;
    private Integer page;
    private Integer size;
}
