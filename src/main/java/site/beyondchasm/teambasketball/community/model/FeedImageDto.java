package site.beyondchasm.teambasketball.community.model;

import lombok.Data;

@Data
public class FeedImageDto {
    private long feed_id;
    private long seq;
    private String image_path;
    private String is_main;

}