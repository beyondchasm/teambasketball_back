package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class FeedImageCommand {
	private Long feed_id;
	private Long seq;
	private String image_path;
	private String is_main;
}
