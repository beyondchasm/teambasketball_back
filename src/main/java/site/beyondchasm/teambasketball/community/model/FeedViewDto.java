package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class FeedViewDto {
	private long feed_id;
	private long user_id;
	private Date created_at;
}