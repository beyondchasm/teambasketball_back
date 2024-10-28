package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class FeedViewDto {

  private long feedId;
  private long userId;
  private Date createdAt;
}
