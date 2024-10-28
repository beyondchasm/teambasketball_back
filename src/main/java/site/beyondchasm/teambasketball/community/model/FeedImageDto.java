package site.beyondchasm.teambasketball.community.model;

import lombok.Data;

@Data
public class FeedImageDto {

  private long feedId;
  private long seq;
  private String imagePath;
  private String isMain;

}