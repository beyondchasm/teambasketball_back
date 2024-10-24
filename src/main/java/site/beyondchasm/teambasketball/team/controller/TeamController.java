package site.beyondchasm.teambasketball.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.team.command.*;
import site.beyondchasm.teambasketball.team.model.TeamDto;
import site.beyondchasm.teambasketball.team.model.TeamMemberDto;
import site.beyondchasm.teambasketball.team.model.TeamScheduleInfoDto;
import site.beyondchasm.teambasketball.team.service.TeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    // 팀 신규등록 API
    @PostMapping("/createTeam")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamCreateCommand teamCreateCommand) {

        TeamDto teamDto = teamService.createTeam(teamCreateCommand);
        return ResponseEntity.ok(teamDto);
    }

    // 팀 신규등록 API
    @PostMapping("/editTeam")
    public ResponseEntity<TeamDto> editTeam(@RequestBody TeamUpdateCommand teamUpdateCommand) {
        TeamDto teamDto = teamService.editTeam(teamUpdateCommand);
        return ResponseEntity.ok(teamDto);
    }

    // 유저정보 조회 API
    @PostMapping("/list")
    public List<TeamDto> list(@RequestBody TeamFilterCommand teamFilterCommand) {
        return teamService.getTeamList(teamFilterCommand);
    }

    // 플레이어 상세 정보 조회 API
    @GetMapping("/detail/{team_id}")
    public ResponseEntity<TeamDto> getTeamDetail(@PathVariable Long team_id) {
        TeamDto teamDetail = teamService.getTeamDetail(team_id);
        if (teamDetail == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }
        return ResponseEntity.ok(teamDetail);
    }

    // 플레이어 상세 정보 조회 API
    @GetMapping("/members/{team_id}")
    public ResponseEntity<List<TeamMemberDto>> getTeamMembrs(@PathVariable Long team_id) {
        List<TeamMemberDto> teamMemberList = teamService.getTeamMembrs(team_id);
        if (teamMemberList == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }
        return ResponseEntity.ok(teamMemberList);
    }


    @PostMapping("/applyTeam")
    public ResponseEntity<Boolean> applyTeam(@RequestBody TeamApplyCommand command) {
        Boolean rtnVal = teamService.applyTeam(command);
        return ResponseEntity.ok(rtnVal);
    }


}
