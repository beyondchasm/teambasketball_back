package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class CommentLikeDto {

  private long commentId;
  private long userId;
  private Date createdAt;

}
