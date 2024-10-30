package site.beyondchasm.teambasketball.match.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;
import lombok.Data;
import site.beyondchasm.teambasketball.court.model.CourtDto;

/**
 * 매치 정보를 담는 DTO 클래스.
 */
@Data
public class MatchDto {

  /**
   * 매치 ID
   */
  @Schema(description = "매치 ID", example = "1")
  private int matchId;

  /**
   * 매치 유형 (예: 5대5, 3대3 등)
   */
  @Schema(description = "매치 유형", example = "5대5")
  private String matchType;

  /**
   * 매치 세부 유형
   */
  @Schema(description = "매치 세부 유형", example = "친선 경기")
  private String matchDetailType;

  /**
   * 매치가 열리는 코트 정보
   */
  @Schema(description = "매치 코트 정보")
  private CourtDto court;

  /**
   * 지역 코드
   */
  @Schema(description = "매치가 열리는 지역 코드", example = "SEOUL")
  private String regionCode;

  /**
   * 매치 날짜
   */
  @Schema(description = "매치 날짜", example = "2024-12-25")
  private Date matchDate;

  /**
   * 매치 시작 시간
   */
  @Schema(description = "매치 시작 시간", example = "2024-12-25T10:00:00")
  private Date startTime;

  /**
   * 매치 종료 시간
   */
  @Schema(description = "매치 종료 시간", example = "2024-12-25T12:00:00")
  private Date endTime;

  /**
   * 매치 참가비
   */
  @Schema(description = "매치 참가비", example = "5000")
  private int joinFee;

  /**
   * 매치 참여 멤버 리스트
   */
  @Schema(description = "매치 참여 멤버 리스트")
  private List<MatchMemberDto> members;

  /**
   * 매치 생성 시간
   */
  @Schema(description = "매치 생성 시간", example = "2024-10-30T08:00:00")
  private Date createdAt;

  /**
   * 매치 수정 시간
   */
  @Schema(description = "매치 수정 시간", example = "2024-10-30T08:00:00")
  private Date updatedAt;

  /**
   * 매치 설명
   */
  @Schema(description = "매치 설명", example = "서울 강남구에서 열리는 5대5 농구 매치")
  private String description;

  /**
   * 매치 상태
   */
  @Schema(description = "매치 상태", example = "Scheduled")
  private String status;

  /**
   * 최대 참여 인원
   */
  @Schema(description = "최대 참여 인원", example = "10")
  private int maxMemberCount;

  /**
   * 매치 주최자 ID
   */
  @Schema(description = "매치 주최자 ID", example = "42")
  private int hostUserId;
}
