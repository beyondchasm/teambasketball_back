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
   * 팀 생성 API POST /api/teams
   */
  @PostMapping
  public ResponseEntity<TeamDto> createTeam(@RequestBody TeamCreateCommand teamCreateCommand) {
    TeamDto teamDto = teamService.createTeam(teamCreateCommand);
    return ResponseEntity.status(201).body(teamDto);
  }

  /**
   * 팀 수정 API PUT /api/teams/{team_id}
   */
  @PutMapping("/{team_id}")
  public ResponseEntity<TeamDto> editTeam(@PathVariable Long team_id,
      @RequestBody TeamUpdateCommand teamUpdateCommand) {
    teamUpdateCommand.setTeam_id(team_id); // Command 객체에 ID 설정
    TeamDto teamDto = teamService.editTeam(teamUpdateCommand);
    return ResponseEntity.ok(teamDto);
  }

  /**
   * 팀 목록 조회 API GET /api/teams
   */
  @GetMapping
  public ResponseEntity<List<TeamDto>> list(TeamFilterCommand teamFilterCommand) {
    List<TeamDto> teamList = teamService.getTeamList(teamFilterCommand);
    return ResponseEntity.ok(teamList);
  }

  /**
   * 팀 상세 정보 조회 API GET /api/teams/{team_id}
   */
  @GetMapping("/{team_id}")
  public ResponseEntity<TeamDto> getTeamDetail(@PathVariable Long team_id) {
    TeamDto teamDetail = teamService.getTeamDetail(team_id);
    if (teamDetail == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(teamDetail);
  }

  /**
   * 팀 멤버 목록 조회 API GET /api/teams/{team_id}/members
   */
  @GetMapping("/{team_id}/members")
  public ResponseEntity<List<TeamMemberDto>> getTeamMembers(@PathVariable Long team_id) {
    List<TeamMemberDto> teamMemberList = teamService.getTeamMembrs(team_id);
    if (teamMemberList == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(teamMemberList);
  }

  /**
   * 팀 가입 신청 API POST /api/teams/{team_id}/apply
   */
  @PostMapping("/{team_id}/apply")
  public ResponseEntity<Boolean> applyTeam(@PathVariable Long team_id,
      @RequestBody TeamApplyCommand command) {
    command.setTeam_id(team_id); // Command 객체에 team_id 설정
    Boolean rtnVal = teamService.applyTeam(command);
    return ResponseEntity.ok(rtnVal);
  }
}
