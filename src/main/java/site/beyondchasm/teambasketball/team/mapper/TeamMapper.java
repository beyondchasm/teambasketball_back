package site.beyondchasm.teambasketball.team.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.team.command.*;
import site.beyondchasm.teambasketball.team.model.*;

@Mapper
public interface TeamMapper {

  /**
   * 필터 조건에 맞는 팀의 개수를 조회합니다.
   *
   * @param filter 팀 필터 조건
   * @return 조회된 팀의 개수
   */
  long getTeamListCount(@Param("filter") TeamFilterCommand filter);

  /**
   * 필터 조건에 맞는 팀 목록을 조회합니다.
   *
   * @param filter 팀 필터 조건
   * @return 조회된 팀 목록
   */
  List<TeamDto> getTeamList(@Param("filter") TeamFilterCommand filter);

  /**
   * 특정 팀의 상세 정보를 조회합니다.
   *
   * @param teamId 팀 ID
   * @return 조회된 팀 상세 정보
   */
  TeamDto getTeamDetail(Long teamId);

  /**
   * 새로운 팀을 생성합니다.
   *
   * @param teamCreateCommand 생성할 팀 정보
   */
  void createTeam(TeamCreateCommand teamCreateCommand);

  /**
   * 팀 멤버를 추가합니다.
   *
   * @param teamMemberCommand 추가할 팀 멤버 정보
   */
  void addTeamMember(TeamMemberCommand teamMemberCommand);

  /**
   * 특정 사용자가 소속된 팀 정보를 조회합니다.
   *
   * @param userId 사용자 ID
   * @return 사용자가 소속된 팀 정보
   */
  MyTeamDto getMyTeamDetail(Long userId);

  /**
   * 특정 팀에 소속된 멤버 목록을 조회합니다.
   *
   * @param teamId 팀 ID
   * @return 조회된 팀 멤버 목록
   */
  List<TeamMemberDto> getTeamMembers(Long teamId);

  /**
   * 특정 사용자의 팀 멤버 정보를 조회합니다.
   *
   * @param userId 사용자 ID
   * @return 조회된 팀 멤버 정보
   */

  TeamMemberDto getMyMemberInfo(Long userId);

  /**
   * 팀 정보를 수정합니다.
   *
   * @param teamUpdateCommand 수정할 팀 정보
   */
  void editTeam(TeamUpdateCommand teamUpdateCommand);

  /**
   * 팀의 나이대 범위를 추가합니다.
   *
   * @param teamAgeRangeCommand 추가할 나이대 범위 정보
   */
  void addTeamAgeRange(TeamAgeRangeCommand teamAgeRangeCommand);

  /**
   * 팀의 활동 요일을 추가합니다.
   *
   * @param teamActDayCommand 추가할 활동 요일 정보
   */
  void addTeamActDay(TeamActDayCommand teamActDayCommand);

  /**
   * 팀의 활동 시간을 추가합니다.
   *
   * @param teamActTimeCommand 추가할 활동 시간 정보
   */
  void addTeamActTime(TeamActTimeCommand teamActTimeCommand);

  /**
   * 특정 팀의 나이대 범위를 삭제합니다.
   *
   * @param teamId 팀 ID
   */
  void deleteTeamAgeRange(Long teamId);

  /**
   * 특정 팀의 활동 요일을 삭제합니다.
   *
   * @param teamId 팀 ID
   */
  void deleteTeamActDay(Long teamId);

  /**
   * 특정 팀의 활동 시간을 삭제합니다.
   *
   * @param teamId 팀 ID
   */
  void deleteTeamActTime(Long teamId);
}
