package site.beyondchasm.teambasketball.match.command;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MatchUpdateCommand {

  private long matchId;
  private String matchType;
  private String matchDetailType;
  private long courtId;
  private String regionCode;
  private LocalDate matchDate;
  private LocalDate startTime; // 매치 시작 시간
  private LocalDate endTime; // 매치 종료 시간
  private int joinFee;
  private String description; // 매치 설명
  private int maxMemberCount; // 최대 참여 인원
}
