package site.beyondchasm.teambasketball.community.model;

import lombok.Data;

@Data
public class ChannelDto {
    private long channel_id;
    private String channel_name;
    private String channel_type;
    private int feed_cnt;
}
