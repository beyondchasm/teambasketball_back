package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class FeedReactionDto {

  private long feedId;
  private long userId;
  private String type;
  private Date createdAt;

}
