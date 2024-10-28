package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {

  private long commentId;
  private long feedId;
  private long userId;
  private String content;
  private Date createdAt;
  private Date updatedAt;
}
