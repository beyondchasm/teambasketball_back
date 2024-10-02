package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class CommentCommand {
	private Long comment_id;
	private Long feed_id;
	private Long user_id;
	private String content;
}
