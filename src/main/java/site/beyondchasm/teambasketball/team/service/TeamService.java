package site.beyondchasm.teambasketball.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.beyondchasm.teambasketball.auth.model.UserPrincipal;
import site.beyondchasm.teambasketball.team.command.*;
import site.beyondchasm.teambasketball.team.mapper.TeamMapper;
import site.beyondchasm.teambasketball.team.model.TeamDto;
import site.beyondchasm.teambasketball.team.model.TeamMemberDto;
import site.beyondchasm.teambasketball.team.model.TeamPhotoDto;
import site.beyondchasm.teambasketball.team.model.TeamScheduleInfoDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamMapper teamMapper;

    public List<TeamDto> getTeamList(TeamFilterCommand filterCommand) {
        List<TeamDto> teams = teamMapper.getTeamList(filterCommand);
        long totalElements = teamMapper.getTeamListCount(filterCommand); // 전체 요소 개수 조회

        return teams;
    }

    public TeamDto getTeamDetail(Long team_id) {
        return teamMapper.getTeamDetail(team_id);
    }

    @Transactional
    public TeamDto createTeam(TeamCommand teamCommand) {
        teamMapper.createTeam(teamCommand); // TeamCommand를 이용해 팀 생성

        Long createdTeamId = teamCommand.getTeam_id(); // 생성된 팀의 ID 가져오기

        // 현재 로그인한 사용자 정보 얻기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;
        if (principal instanceof UserPrincipal) {
            userId = ((UserPrincipal) principal).getId();
        }

        if (userId != null) {
            TeamMemberCommand teamMemberCommand = new TeamMemberCommand();
            teamMemberCommand.setUser_id(userId);
            teamMemberCommand.setTeam_id(createdTeamId);
            teamMemberCommand.setRole("03"); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

            // 팀 유저 테이블에 사용자 추가
            teamMapper.addTeamMember(teamMemberCommand);
        }

        return getTeamDetail(createdTeamId); // 생성된 팀의 상세 정보 조회
    }

    public TeamDto editTeam(TeamCommand teamCommand) {
        teamMapper.editTeam(teamCommand); // TeamCommand를 이용해 팀 생성
        return getTeamDetail(teamCommand.getTeam_id());
    }

    public List<TeamDto> getMyTeamList() {
        // 현재 로그인한 사용자 정보 얻기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;
        if (principal instanceof UserPrincipal) {
            userId = ((UserPrincipal) principal).getId();
        }

        List<TeamDto> myTeams = teamMapper.getMyTeamList(userId);
        return myTeams;
    }

    public List<TeamMemberDto> getTeamMemberList(Long team_id) {
        return teamMapper.getTeamMemberList(team_id);
    }

    public TeamScheduleInfoDto addTeamSchedule(TeamScheduleInfoCommand command) {
        teamMapper.addTeamSchedule(command); // TeamCommand를 이용해 팀 생성
        TeamScheduleInfoDto teamScheduleInfoDto = new TeamScheduleInfoDto();
        return teamScheduleInfoDto;
    }

    public List<TeamScheduleInfoDto> getTeamScheduleList(Long team_id) {
        return teamMapper.getTeamScheduleList(team_id);
    }

    public TeamScheduleInfoDto getTeamScheduleDetail(Long team_id, Long seq) {
        return teamMapper.getTeamScheduleDetail(team_id, seq);
    }

    public TeamPhotoDto addTeamPhotos(TeamPhotoCommand command) {
        // 트랜잭션 시작
        try {
            // 기존 사진 삭제
            teamMapper.deleteTeamPhotos(command.getTeam_id());

            teamMapper.addTeamPhotos(command); // TeamCommand를 이용해 팀 생성

            // 트랜잭션 커밋
        } catch (Exception e) {
            // 예외 발생 시 트랜잭션 롤백
        }

        TeamPhotoDto teamPhotoDto = new TeamPhotoDto();
        return teamPhotoDto;
    }

    public TeamPhotoDto getTeamPhotoDetail(Long team_id, Long seq) {
        return teamMapper.getTeamPhotoDetail(team_id, seq);
    }

    public List<TeamPhotoDto> getTeamPhotosList(Long team_id) {
        return teamMapper.getTeamPhotosList(team_id);
    }

}
