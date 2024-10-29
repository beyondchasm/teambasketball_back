package site.beyondchasm.teambasketball.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.team.command.*;
import site.beyondchasm.teambasketball.team.model.TeamDto;
import site.beyondchasm.teambasketball.team.model.TeamMemberDto;
import site.beyondchasm.teambasketball.team.service.TeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

  private final TeamService teamService;

  /**
   * 새로운 팀을 생성합니다.
   *
   * @param teamCreateCommand 생성할 팀 정보
   * @return 생성된 팀 정보
   */
  @PostMapping
  public ResponseEntity<TeamDto> createTeam(@RequestBody TeamCreateCommand teamCreateCommand) {
    TeamDto teamDto = teamService.createTeam(teamCreateCommand);
    return ResponseEntity.status(201).body(teamDto);
  }

  /**
   * 팀 정보를 수정합니다.
   *
   * @param teamId            수정할 팀의 ID
   * @param teamUpdateCommand 수정할 팀 정보
   * @return 수정된 팀 정보
   */
  @PutMapping("/{teamId}")
  public ResponseEntity<TeamDto> editTeam(@PathVariable Long teamId,
      @RequestBody TeamUpdateCommand teamUpdateCommand) {
    teamUpdateCommand.setTeamId(teamId); // Command 객체에 ID 설정
    TeamDto teamDto = teamService.editTeam(teamUpdateCommand);
    return ResponseEntity.ok(teamDto);
  }

  /**
   * 팀 목록을 조회합니다.
   *
   * @param teamFilterCommand 팀 목록 필터 조건
   * @return 필터링된 팀 목록
   */
  @GetMapping
  public ResponseEntity<List<TeamDto>> list(TeamFilterCommand teamFilterCommand) {
    List<TeamDto> teamList = teamService.getTeamList(teamFilterCommand);
    return ResponseEntity.ok(teamList);
  }

  /**
   * 특정 팀의 상세 정보를 조회합니다.
   *
   * @param teamId 조회할 팀의 ID
   * @return 조회된 팀의 상세 정보
   */
  @GetMapping("/{teamId}")
  public ResponseEntity<TeamDto> getTeamDetail(@PathVariable Long teamId) {
    TeamDto teamDetail = teamService.getTeamDetail(teamId);
    if (teamDetail == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(teamDetail);
  }

  /**
   * 특정 팀의 멤버 목록을 조회합니다.
   *
   * @param teamId 조회할 팀의 ID
   * @return 팀 멤버 목록
   */
  @GetMapping("/{teamId}/members")
  public ResponseEntity<List<TeamMemberDto>> getTeamMembers(@PathVariable Long teamId) {
    List<TeamMemberDto> teamMemberList = teamService.getTeamMembers(teamId);
    if (teamMemberList == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(teamMemberList);
  }

  /**
   * 팀 가입 신청을 수행합니다.
   *
   * @param teamId  신청할 팀의 ID
   * @param command 팀 가입 신청 정보
   * @return 가입 신청 성공 여부
   */
  @PostMapping("/{teamId}/apply")
  public ResponseEntity<Boolean> applyTeam(@PathVariable Long teamId,
      @RequestBody TeamApplyCommand command) {
    command.setTeamId(teamId); // Command 객체에 teamId 설정
    Boolean rtnVal = teamService.applyTeam(command);
    return ResponseEntity.ok(rtnVal);
  }
}
