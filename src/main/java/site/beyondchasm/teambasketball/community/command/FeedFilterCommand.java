package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class FeedFilterCommand {

  private long loginedUserId;
  private String searchKeyword;
  private Long channelId;
  private Long teamId;
  private String sort;
  private Integer page;
  private Integer size;
}
