package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class FeedDto {
	private long feed_id;
	private long channel_id;
	private String title;
	private String content_type;
	private String content;
	private long user_id;
	private Date created_at;
	private Date updated_at;
	private int view_cnt;
}
