package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class CommentLikeDto {
	private long comment_id;
	private long user_id;
	private Date created_at;

}
