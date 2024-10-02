package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {
	private long comment_id;
	private long feed_id;
	private long user_id;
	private String content;
	private Date created_at;
	private Date updated_at;
}
