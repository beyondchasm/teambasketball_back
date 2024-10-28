package site.beyondchasm.teambasketball.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class FeedDto {

  private long feedId;
  private long channelId;
  private Boolean isTeam;
  private long teamId;
  private String title;
  private String contentType;
  private String content;
  private long userId;
  private Date createdAt;
  private Date updatedAt;
  private int viewCnt;
  private String isReaction;
  private int likeCnt;
  private int dislikeCnt;
  private int commentCnt;
}
