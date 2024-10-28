package site.beyondchasm.teambasketball.match.command;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MatchCreateCommand {

  private long matchId;
  private String matchType;
  private String matchDetailType;
  private long courtId;
  private String regionCode;
  private LocalDate matchDate;
  private LocalDateTime startTime; // 매치 시작 시간
  private LocalDateTime endTime; // 매치 종료 시간
  private int joinFee;
  private String status;
  private String description; // 매치 설명
  private int maxMemberCount; // 최대 참여 인원
  private long hostUserId;
}
