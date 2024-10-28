package site.beyondchasm.teambasketball.community.model;

import lombok.Data;

@Data
public class ChannelDto {

  private long channelId;
  private String channelName;
  private String channelType;
  private int feedCnt;
}
