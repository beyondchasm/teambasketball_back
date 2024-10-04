package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class CommentCommand {
    private Long feed_id;
    private String content;
}
