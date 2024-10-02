package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class FeedCommand {
	private Long feed_id;
	private Long channel_id;
	private Long user_id;
	private String title;
	private String content_type;
	private String content;
}
