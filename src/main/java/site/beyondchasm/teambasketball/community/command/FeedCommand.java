package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class FeedCommand {

  private Boolean isTeam;
  private Long channelId;
  private long teamId;
  private long feedId;
  private String title;
  private String contentType;
  private String content;
}
