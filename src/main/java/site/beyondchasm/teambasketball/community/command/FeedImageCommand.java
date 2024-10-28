package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class FeedImageCommand {

  private Long feedId;
  private Long seq;
  private String imagePath;
  private String isMain;
}
