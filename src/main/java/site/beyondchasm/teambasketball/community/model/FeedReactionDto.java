package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class FeedReactionDto {
    private long feed_id;
    private long user_id;
    private String type;
    private Date created_at;

}
