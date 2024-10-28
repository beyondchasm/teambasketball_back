package site.beyondchasm.teambasketball.match.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.beyondchasm.teambasketball.auth.enums.MatchMemberStatus;
import site.beyondchasm.teambasketball.auth.enums.MatchOverallStatus;
import site.beyondchasm.teambasketball.auth.model.UserPrincipal;
import site.beyondchasm.teambasketball.match.command.MatchCreateCommand;
import site.beyondchasm.teambasketball.match.command.MatchFilterCommand;
import site.beyondchasm.teambasketball.match.command.MatchMemberCommand;
import site.beyondchasm.teambasketball.match.command.MatchUpdateCommand;
import site.beyondchasm.teambasketball.match.mapper.MatchMapper;
import site.beyondchasm.teambasketball.match.model.MatchDto;
import site.beyondchasm.teambasketball.match.model.MatchMemberDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

  private final MatchMapper matchMapper;

  /**
   * 필터 조건에 맞는 매치 목록을 조회합니다.
   *
   * @param filterCommand 매치 필터 조건
   * @return 조회된 매치 목록
   */
  public List<MatchDto> getMatchList(MatchFilterCommand filterCommand) {
    return matchMapper.getMatchList(filterCommand);
  }

  /**
   * 특정 매치의 상세 정보를 조회합니다.
   *
   * @param matchId 매치 ID
   * @return 조회된 매치 상세 정보
   */
  public MatchDto getMatchDetail(Long matchId) {
    return matchMapper.getMatchDetail(matchId);
  }

  /**
   * 새로운 매치를 생성하고 생성된 매치의 ID를 사용하여 매치 정보를 반환합니다.
   *
   * @param matchCreateCommand 생성할 매치 정보
   * @return 생성된 매치 정보
   */
  @Transactional
  public MatchDto createMatch(MatchCreateCommand matchCreateCommand) {
    Long userId = getCurrentUserId();
    matchCreateCommand.setHostUserId(userId);
    matchCreateCommand.setStatus(MatchOverallStatus.RECRUITING.getStatus());
    matchMapper.createMatch(matchCreateCommand);
    Long createdMatchId = matchCreateCommand.getMatchId();

    if (userId != null) {
      MatchMemberCommand matchMemberCommand = new MatchMemberCommand();
      matchMemberCommand.setUserId(userId);
      matchMemberCommand.setMatchId(createdMatchId);
      matchMemberCommand.setStatus(MatchMemberStatus.APPROVED.getStatus());
      matchMapper.addMatchMember(matchMemberCommand);
    }

    return getMatchDetail(createdMatchId);
  }

  /**
   * 매치 정보를 수정하고 수정된 매치 정보를 반환합니다.
   *
   * @param matchUpdateCommand 수정할 매치 정보
   * @return 수정된 매치 정보
   */
  public MatchDto editMatch(MatchUpdateCommand matchUpdateCommand) {
    matchMapper.editMatch(matchUpdateCommand);
    return getMatchDetail(matchUpdateCommand.getMatchId());
  }

  /**
   * 특정 매치에 소속된 멤버 목록을 조회합니다.
   *
   * @param matchId 매치 ID
   * @return 매치 멤버 목록
   */
  public List<MatchMemberDto> getMatchMembers(Long matchId) {
    return matchMapper.getMatchMembers(matchId);
  }

  /**
   * 현재 로그인한 사용자가 특정 매치에 참가 신청을 합니다.
   *
   * @param matchId 매치 ID
   * @return 참가 신청 성공 여부
   */
  public Boolean applyMatch(Long matchId) {
    try {
      Long userId = getCurrentUserId();
      if (userId != null) {
        MatchMemberCommand matchMemberCommand = new MatchMemberCommand();
        matchMemberCommand.setMatchId(matchId);
        matchMemberCommand.setUserId(userId);
        matchMemberCommand.setStatus(MatchMemberStatus.APPLIED.getStatus());
        matchMapper.addMatchMember(matchMemberCommand);
      }
      return true;
    } catch (Exception e) {
      System.err.println("Error while applying to match: " + e.getMessage());
      return false;
    }
  }

  /**
   * 매치에서 특정 멤버를 삭제합니다.
   *
   * @param matchId 매치 ID
   * @param userId  삭제할 사용자 ID
   */
  @Transactional
  public void removeMatchMember(Long matchId, Long userId) {
    matchMapper.removeMatchMember(matchId, userId);
  }

  /**
   * 매치 멤버의 상태를 변경합니다.
   *
   * @param matchId 매치 ID
   * @param userId  사용자 ID
   * @param status  변경할 상태
   */
  @Transactional
  public void updateMatchMemberStatus(Long matchId, Long userId, String status) {
    matchMapper.updateMatchMemberStatus(matchId, userId, status);
  }

  /**
   * 현재 로그인한 사용자의 ID를 반환합니다.
   *
   * @return 로그인한 사용자 ID
   */
  private Long getCurrentUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserPrincipal) {
      return ((UserPrincipal) principal).getId();
    }
    return null;
  }
}
