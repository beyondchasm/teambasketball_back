package site.beyondchasm.teambasketball.community.command;

import lombok.Data;

@Data
public class CommentCommand {

  private Long feedId;
  private Long commentId;
  private String content;
}
