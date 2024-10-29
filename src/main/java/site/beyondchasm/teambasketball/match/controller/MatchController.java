package site.beyondchasm.teambasketball.match.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.match.command.MatchCreateCommand;
import site.beyondchasm.teambasketball.match.command.MatchFilterCommand;
import site.beyondchasm.teambasketball.match.command.MatchUpdateCommand;
import site.beyondchasm.teambasketball.match.model.MatchDto;
import site.beyondchasm.teambasketball.match.model.MatchMemberDto;
import site.beyondchasm.teambasketball.match.service.MatchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches")
public class MatchController {

  private final MatchService matchService;

  /**
   * 새로운 매치를 생성합니다.
   *
   * @param matchCreateCommand 생성할 매치 정보
   * @return 생성된 매치 정보
   */
  @PostMapping
  public ResponseEntity<MatchDto> createMatch(@RequestBody MatchCreateCommand matchCreateCommand) {
    MatchDto matchDto = matchService.createMatch(matchCreateCommand);
    return ResponseEntity.status(201).body(matchDto);
  }

  /**
   * 매치를 수정합니다.
   *
   * @param matchId            수정할 매치 ID
   * @param matchUpdateCommand 수정할 매치 정보
   * @return 수정된 매치 정보
   */
  @PutMapping("/{matchId}")
  public ResponseEntity<MatchDto> editMatch(@PathVariable Long matchId,
      @RequestBody MatchUpdateCommand matchUpdateCommand) {
    matchUpdateCommand.setMatchId(matchId); // Command 객체에 ID 설정
    MatchDto matchDto = matchService.editMatch(matchUpdateCommand);
    return ResponseEntity.ok(matchDto);
  }

  /**
   * 매치 목록을 조회합니다.
   *
   * @param matchFilterCommand 매치 필터 조건
   * @return 필터링된 매치 목록
   */
  @GetMapping
  public ResponseEntity<List<MatchDto>> list(MatchFilterCommand matchFilterCommand) {
    List<MatchDto> matchList = matchService.getMatchList(matchFilterCommand);
    return ResponseEntity.ok(matchList);
  }

  /**
   * 특정 매치의 상세 정보를 조회합니다.
   *
   * @param matchId 조회할 매치 ID
   * @return 조회된 매치 상세 정보
   */
  @GetMapping("/{matchId}")
  public ResponseEntity<MatchDto> getMatchDetail(@PathVariable Long matchId) {
    MatchDto matchDetail = matchService.getMatchDetail(matchId);
    if (matchDetail == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(matchDetail);
  }

  /**
   * 특정 매치의 멤버 목록을 조회합니다.
   *
   * @param matchId 조회할 매치 ID
   * @return 매치 멤버 목록
   */
  @GetMapping("/{matchId}/members")
  public ResponseEntity<List<MatchMemberDto>> getMatchMembers(@PathVariable Long matchId) {
    List<MatchMemberDto> matchMemberList = matchService.getMatchMembers(matchId);
    if (matchMemberList == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(matchMemberList);
  }

  /**
   * 매치에 참여 신청을 합니다.
   *
   * @param matchId 참여 신청할 매치 ID
   * @return 신청 성공 여부
   */
  @PatchMapping("/{matchId}/apply")
  public ResponseEntity<Boolean> applyMatch(@PathVariable Long matchId) {
    Boolean result = matchService.applyMatch(matchId);
    return ResponseEntity.ok(result);
  }

  /**
   * 매치 멤버를 삭제합니다.
   *
   * @param matchId 삭제할 매치 ID
   * @param userId  삭제할 멤버의 사용자 ID
   * @return 삭제 성공 응답
   */
  @DeleteMapping("/{matchId}/members/{user_id}")
  public ResponseEntity<Void> removeMatchMember(@PathVariable Long matchId,
      @PathVariable Long userId) {
    matchService.removeMatchMember(matchId, userId);
    return ResponseEntity.noContent().build();
  }

  /**
   * 매치 멤버의 상태를 변경합니다.
   *
   * @param matchId 매치 ID
   * @param userId  상태를 변경할 멤버의 사용자 ID
   * @param status  변경할 상태
   * @return 상태 변경 성공 응답
   */
  @PatchMapping("/{matchId}/members/{user_id}/status")
  public ResponseEntity<Void> updateMatchMemberStatus(@PathVariable Long matchId,
      @PathVariable Long userId,
      @RequestParam String status) {
    matchService.updateMatchMemberStatus(matchId, userId, status);
    return ResponseEntity.noContent().build();
  }
}
