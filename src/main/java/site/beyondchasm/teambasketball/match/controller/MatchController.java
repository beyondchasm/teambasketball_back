package site.beyondchasm.teambasketball.match.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
   * 매치 생성 API POST /api/matches
   */
  @PostMapping
  public ResponseEntity<MatchDto> createMatch(@RequestBody MatchCreateCommand matchCreateCommand) {
    MatchDto matchDto = matchService.createMatch(matchCreateCommand);
    return ResponseEntity.status(201).body(matchDto);
  }

  /**
   * 매치 수정 API PUT /api/matches/{match_id}
   */
  @PutMapping("/{match_id}")
  public ResponseEntity<MatchDto> editMatch(@PathVariable Long match_id,
      @RequestBody MatchUpdateCommand matchUpdateCommand) {
    matchUpdateCommand.setMatch_id(match_id); // Command 객체에 ID 설정
    MatchDto matchDto = matchService.editMatch(matchUpdateCommand);
    return ResponseEntity.ok(matchDto);
  }

  /**
   * 매치 리스트 조회 API GET /api/matches
   */
  @GetMapping
  public ResponseEntity<List<MatchDto>> list(MatchFilterCommand matchFilterCommand) {
    List<MatchDto> matchList = matchService.getMatchList(matchFilterCommand);
    return ResponseEntity.ok(matchList);
  }

  /**
   * 매치 상세 정보 조회 API GET /api/matches/{match_id}
   */
  @GetMapping("/{match_id}")
  public ResponseEntity<MatchDto> getMatchDetail(@PathVariable Long match_id) {
    MatchDto matchDetail = matchService.getMatchDetail(match_id);
    if (matchDetail == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(matchDetail);
  }

  /**
   * 매치 멤버 리스트 조회 API GET /api/matches/{match_id}/members
   */
  @GetMapping("/{match_id}/members")
  public ResponseEntity<List<MatchMemberDto>> getMatchMembers(@PathVariable Long match_id) {
    List<MatchMemberDto> matchMemberList = matchService.getMatchMembrs(match_id);
    if (matchMemberList == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(matchMemberList);
  }

  /**
   * 매치 신청 API PATCH /api/matches/{match_id}/apply
   */
  @PatchMapping("/{match_id}/apply")
  public ResponseEntity<Boolean> applyMatch(@PathVariable Long match_id) {
    Boolean result = matchService.applyMatch(match_id);
    return ResponseEntity.ok(result);
  }

  /**
   * 매치 멤버 삭제 API DELETE /api/matches/{match_id}/members/{user_id}
   */
  @DeleteMapping("/{match_id}/members/{user_id}")
  public ResponseEntity<Void> removeMatchMember(@PathVariable Long match_id,
      @PathVariable Long user_id) {
    matchService.removeMatchMember(match_id, user_id);
    return ResponseEntity.noContent().build();
  }

  /**
   * 매치 멤버 상태 변경 API PATCH /api/matches/{match_id}/members/{user_id}/status
   */
  @PatchMapping("/{match_id}/members/{user_id}/status")
  public ResponseEntity<Void> updateMatchMemberStatus(@PathVariable Long match_id,
      @PathVariable Long user_id,
      @RequestParam String status) {
    matchService.updateMatchMemberStatus(match_id, user_id, status);
    return ResponseEntity.noContent().build();
  }
}
