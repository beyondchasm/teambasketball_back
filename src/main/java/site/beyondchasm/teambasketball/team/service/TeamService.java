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

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

  private final TeamMapper teamMapper;

  /**
   * 필터 조건에 맞는 팀 목록을 조회합니다.
   *
   * @param filterCommand 팀 필터 조건
   * @return 조회된 팀 목록
   */
  public List<TeamDto> getTeamList(TeamFilterCommand filterCommand) {
    return teamMapper.getTeamList(filterCommand);
  }

  /**
   * 특정 팀의 상세 정보를 조회합니다.
   *
   * @param teamId 팀 ID
   * @return 조회된 팀 상세 정보
   */
  public TeamDto getTeamDetail(Long teamId) {
    return teamMapper.getTeamDetail(teamId);
  }

  /**
   * 새로운 팀을 생성하고 생성된 팀의 상세 정보를 반환합니다.
   *
   * @param teamCreateCommand 생성할 팀 정보
   * @return 생성된 팀의 상세 정보
   */
  @Transactional
  public TeamDto createTeam(TeamCreateCommand teamCreateCommand) {
    teamMapper.createTeam(teamCreateCommand);
    Long createdTeamId = teamCreateCommand.getTeamId();

    Long userId = getCurrentUserId();
    if (userId != null) {
      TeamMemberCommand teamMemberCommand = new TeamMemberCommand();
      teamMemberCommand.setUserId(userId);
      teamMemberCommand.setTeamId(createdTeamId);
      teamMemberCommand.setRole(TeamMemberRole.ADMIN.getRole());
      teamMapper.addTeamMember(teamMemberCommand);
    }

    addTeamAdditionalInfo(teamCreateCommand, createdTeamId);
    return getTeamDetail(createdTeamId);
  }

  /**
   * 팀 정보를 수정하고 수정된 팀 정보를 반환합니다.
   *
   * @param teamUpdateCommand 수정할 팀 정보
   * @return 수정된 팀의 상세 정보
   */
  public TeamDto editTeam(TeamUpdateCommand teamUpdateCommand) {
    teamMapper.editTeam(teamUpdateCommand);
    updateTeamAdditionalInfo(teamUpdateCommand);
    return getTeamDetail(teamUpdateCommand.getTeamId());
  }

  /**
   * 특정 팀에 소속된 멤버 목록을 조회합니다.
   *
   * @param teamId 팀 ID
   * @return 팀 멤버 목록
   */
  public List<TeamMemberDto> getTeamMembers(Long teamId) {
    return teamMapper.getTeamMembers(teamId);
  }

  /**
   * 사용자가 팀에 참가 신청을 합니다.
   *
   * @param command 참가 신청 정보
   * @return 신청 성공 여부
   */
  public Boolean applyTeam(TeamApplyCommand command) {
    try {
      Long userId = getCurrentUserId();
      if (userId != null) {
        TeamMemberCommand teamMemberCommand = new TeamMemberCommand();
        teamMemberCommand.setTeamId(command.getTeamId());
        teamMemberCommand.setUserId(userId);
        teamMemberCommand.setRole(TeamMemberRole.APPLIED.getRole());
        teamMemberCommand.setIntroduction(command.getIntroduction());
        teamMemberCommand.setApplicatedAt(new Date());
        teamMapper.addTeamMember(teamMemberCommand);
      }
      return true;
    } catch (Exception e) {
      System.err.println("Error during team application: " + e.getMessage());
      return false;
    }
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

  /**
   * 생성된 팀에 추가 정보를 설정합니다.
   *
   * @param teamCreateCommand 생성할 팀 정보
   * @param teamId            생성된 팀의 ID
   */
  private void addTeamAdditionalInfo(TeamCreateCommand teamCreateCommand, Long teamId) {
    if (teamCreateCommand.getAgeRanges() != null) {
      teamCreateCommand.getAgeRanges().forEach(ageRangeCode -> {
        TeamAgeRangeCommand teamAgeRangeCommand = new TeamAgeRangeCommand();
        teamAgeRangeCommand.setTeamId(teamId);
        teamAgeRangeCommand.setCodeId(ageRangeCode);
        teamMapper.addTeamAgeRange(teamAgeRangeCommand);
      });
    }
    if (teamCreateCommand.getActDays() != null) {
      teamCreateCommand.getActDays().forEach(dayCode -> {
        TeamActDayCommand teamActDayCommand = new TeamActDayCommand();
        teamActDayCommand.setTeamId(teamId);
        teamActDayCommand.setCodeId(dayCode);
        teamMapper.addTeamActDay(teamActDayCommand);
      });
    }
    if (teamCreateCommand.getActTimes() != null) {
      teamCreateCommand.getActTimes().forEach(timeCode -> {
        TeamActTimeCommand teamActTimeCommand = new TeamActTimeCommand();
        teamActTimeCommand.setTeamId(teamId);
        teamActTimeCommand.setCodeId(timeCode);
        teamMapper.addTeamActTime(teamActTimeCommand);
      });
    }
  }

  private void addTeamAdditionalInfo(TeamUpdateCommand teamUpdateCommand, Long teamId) {
    if (teamUpdateCommand.getAgeRanges() != null) {
      teamUpdateCommand.getAgeRanges().forEach(ageRangeCode -> {
        TeamAgeRangeCommand teamAgeRangeCommand = new TeamAgeRangeCommand();
        teamAgeRangeCommand.setTeamId(teamId);
        teamAgeRangeCommand.setCodeId(ageRangeCode);
        teamMapper.addTeamAgeRange(teamAgeRangeCommand);
      });
    }
    if (teamUpdateCommand.getActDays() != null) {
      teamUpdateCommand.getActDays().forEach(dayCode -> {
        TeamActDayCommand teamActDayCommand = new TeamActDayCommand();
        teamActDayCommand.setTeamId(teamId);
        teamActDayCommand.setCodeId(dayCode);
        teamMapper.addTeamActDay(teamActDayCommand);
      });
    }
    if (teamUpdateCommand.getActTimes() != null) {
      teamUpdateCommand.getActTimes().forEach(timeCode -> {
        TeamActTimeCommand teamActTimeCommand = new TeamActTimeCommand();
        teamActTimeCommand.setTeamId(teamId);
        teamActTimeCommand.setCodeId(timeCode);
        teamMapper.addTeamActTime(teamActTimeCommand);
      });
    }
  }


  /**
   * 팀 수정 시 추가 정보를 업데이트합니다.
   *
   * @param teamUpdateCommand 수정할 팀 정보
   */
  private void updateTeamAdditionalInfo(TeamUpdateCommand teamUpdateCommand) {
    if (teamUpdateCommand.getAgeRanges() != null) {
      teamMapper.deleteTeamAgeRange(teamUpdateCommand.getTeamId());
      addTeamAdditionalInfo(teamUpdateCommand, teamUpdateCommand.getTeamId());
    }
    if (teamUpdateCommand.getActDays() != null) {
      teamMapper.deleteTeamActDay(teamUpdateCommand.getTeamId());
      addTeamAdditionalInfo(teamUpdateCommand, teamUpdateCommand.getTeamId());
    }
    if (teamUpdateCommand.getActTimes() != null) {
      teamMapper.deleteTeamActTime(teamUpdateCommand.getTeamId());
      addTeamAdditionalInfo(teamUpdateCommand, teamUpdateCommand.getTeamId());
    }
  }
}
