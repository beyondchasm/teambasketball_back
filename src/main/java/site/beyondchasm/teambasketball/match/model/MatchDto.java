package site.beyondchasm.teambasketball.match.model;

import lombok.Data;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.court.model.CourtDto;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

import java.util.Date;
import java.util.List;

@Data
public class MatchDto {

  private int matchId;
  private String matchType;
  private String matchDetailType;
  private CourtDto court;
  private String regionCode;
  private Date matchDate;
  private Date startTime; // 매치 시작 시간
  private Date endTime; // 매치 종료 시간
  private int joinFee;
  private List<MatchMemberDto> members;
  private Date createdAt;
  private Date updatedAt;
  private String description; // 매치 설명
  private String status; // 매치 상태
  private int maxMemberCount; // 최대 참여 인원
  private int hostUserId; // 매치 주최자 ID
}
