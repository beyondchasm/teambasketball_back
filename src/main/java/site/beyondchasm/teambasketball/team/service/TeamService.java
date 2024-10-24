package site.beyondchasm.teambasketball.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.beyondchasm.teambasketball.auth.enums.TeamMemberRole;
import site.beyondchasm.teambasketball.auth.model.UserPrincipal;
import site.beyondchasm.teambasketball.team.command.*;
import site.beyondchasm.teambasketball.team.mapper.TeamMapper;
import site.beyondchasm.teambasketball.team.model.TeamDto;
import site.beyondchasm.teambasketball.team.model.TeamMemberDto;
import site.beyondchasm.teambasketball.team.model.TeamScheduleInfoDto;

import java.time.LocalTime;
import java.util.Date;
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
    public TeamDto createTeam(TeamCreateCommand teamCreateCommand) {
        teamMapper.createTeam(teamCreateCommand); // TeamCommand를 이용해 팀 생성

        Long createdTeamId = teamCreateCommand.getTeam_id(); // 생성된 팀의 ID 가져오기

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
            teamMemberCommand.setRole(TeamMemberRole.ADMIN.getRole()); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

            // 팀 유저 테이블에 사용자 추가
            teamMapper.addTeamMember(teamMemberCommand);
        }
        if (teamCreateCommand.getAge_ranges() != null) {
            for (int i = 0; i < teamCreateCommand.getAge_ranges().size(); i++) {
                TeamAgeRangeCommand teamAgeRangeCommand = new TeamAgeRangeCommand();
                teamAgeRangeCommand.setTeam_id(createdTeamId);
                teamAgeRangeCommand.setCode_id(teamCreateCommand.getAge_ranges().get(i)); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

                // 팀 유저 테이블에 사용자 추가
                teamMapper.addTeamAgeRange(teamAgeRangeCommand);
            }
        }
        if (teamCreateCommand.getAct_days() != null) {
            for (int i = 0; i < teamCreateCommand.getAct_days().size(); i++) {
                TeamActDayCommand teamActDayCommand = new TeamActDayCommand();
                teamActDayCommand.setTeam_id(createdTeamId);
                teamActDayCommand.setCode_id(teamCreateCommand.getAct_days().get(i)); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

                // 팀 유저 테이블에 사용자 추가
                teamMapper.addTeamActDay(teamActDayCommand);
            }
        }
        if (teamCreateCommand.getAct_times() != null) {
            for (int i = 0; i < teamCreateCommand.getAct_times().size(); i++) {
                TeamActTimeCommand teamActTimeCommand = new TeamActTimeCommand();
                teamActTimeCommand.setTeam_id(createdTeamId);
                teamActTimeCommand.setCode_id(teamCreateCommand.getAct_times().get(i)); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

                // 팀 유저 테이블에 사용자 추가
                teamMapper.addTeamActTime(teamActTimeCommand);
            }
        }


        return getTeamDetail(createdTeamId); // 생성된 팀의 상세 정보 조회
    }

    public TeamDto editTeam(TeamUpdateCommand teamUpdateCommand) {
        teamMapper.editTeam(teamUpdateCommand); // TeamCommand를 이용해 팀 생성

        if (teamUpdateCommand.getAge_ranges() != null) {
            teamMapper.deleteTeamAgeRange(teamUpdateCommand.getTeam_id());
            for (int i = 0; i < teamUpdateCommand.getAge_ranges().size(); i++) {
                TeamAgeRangeCommand teamAgeRangeCommand = new TeamAgeRangeCommand();
                teamAgeRangeCommand.setTeam_id(teamUpdateCommand.getTeam_id());
                teamAgeRangeCommand.setCode_id(teamUpdateCommand.getAge_ranges().get(i)); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

                // 팀 유저 테이블에 사용자 추가
                teamMapper.addTeamAgeRange(teamAgeRangeCommand);
            }
        }
        if (teamUpdateCommand.getAct_days() != null) {
            teamMapper.deleteTeamActDay(teamUpdateCommand.getTeam_id());
            for (int i = 0; i < teamUpdateCommand.getAct_days().size(); i++) {
                TeamActDayCommand teamActDayCommand = new TeamActDayCommand();
                teamActDayCommand.setTeam_id(teamUpdateCommand.getTeam_id());
                teamActDayCommand.setCode_id(teamUpdateCommand.getAct_days().get(i)); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

                // 팀 유저 테이블에 사용자 추가
                teamMapper.addTeamActDay(teamActDayCommand);
            }
        }
        if (teamUpdateCommand.getAct_times() != null) {
            teamMapper.deleteTeamActTime(teamUpdateCommand.getTeam_id());
            for (int i = 0; i < teamUpdateCommand.getAct_times().size(); i++) {
                TeamActTimeCommand teamActTimeCommand = new TeamActTimeCommand();
                teamActTimeCommand.setTeam_id(teamUpdateCommand.getTeam_id());
                teamActTimeCommand.setCode_id(teamUpdateCommand.getAct_times().get(i)); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요

                // 팀 유저 테이블에 사용자 추가
                teamMapper.addTeamActTime(teamActTimeCommand);
            }
        }

        return getTeamDetail(teamUpdateCommand.getTeam_id());
    }


    public List<TeamMemberDto> getTeamMembrs(Long team_id) {

        return teamMapper.getTeamMembrs(team_id);
    }


    public Boolean applyTeam(TeamApplyCommand command) {

        try {
            // 현재 로그인한 사용자 정보 얻기
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = null;
            if (principal instanceof UserPrincipal) {
                userId = ((UserPrincipal) principal).getId();
            }

            if (userId != null) {
                TeamMemberCommand teamMemberCommand = new TeamMemberCommand();
                teamMemberCommand.setTeam_id(command.getTeam_id());
                teamMemberCommand.setUser_id(userId);
                teamMemberCommand.setRole(TeamMemberRole.APPLIED.getRole()); // 예시 역할 코드, 실제 역할 코드에 맞게 조정 필요
                teamMemberCommand.setIntroduction(command.getIntroduction());
                teamMemberCommand.setApplicated_at(new Date());

                // 팀 유저 테이블에 사용자 추가
                teamMapper.addTeamMember(teamMemberCommand);
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;

        }

    }
}
