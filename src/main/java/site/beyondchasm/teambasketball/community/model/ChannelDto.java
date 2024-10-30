package site.beyondchasm.teambasketball.community.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 커뮤니티 채널 정보를 담는 데이터 전송 객체.
 * <p>
 * 채널 ID, 이름, 유형, 피드 개수를 포함합니다.
 */
@Data
@Schema(description = "커뮤니티 채널 정보를 담는 데이터 전송 객체")
public class ChannelDto {

  /**
   * 채널의 고유 ID.
   */
  @Schema(description = "채널의 고유 ID")
  private long channelId;

  /**
   * 채널 이름.
   */
  @Schema(description = "채널 이름")
  private String channelName;

  /**
   * 채널 유형 (예: 공지사항, 일반 채널 등).
   */
  @Schema(description = "채널 유형 (예: 공지사항, 일반 채널 등)")
  private String channelType;

  /**
   * 채널 내 피드(게시물) 개수.
   */
  @Schema(description = "채널 내 피드(게시물) 개수")
  private int feedCnt;
}
