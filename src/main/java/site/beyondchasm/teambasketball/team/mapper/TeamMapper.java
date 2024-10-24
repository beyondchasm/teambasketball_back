package site.beyondchasm.teambasketball.team.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.team.command.*;
import site.beyondchasm.teambasketball.team.model.*;

@Mapper
public interface TeamMapper {

    long getTeamListCount(@Param("filter") TeamFilterCommand filter);

    List<TeamDto> getTeamList(@Param("filter") TeamFilterCommand filter);

    TeamDto getTeamDetail(Long team_id);

    void createTeam(TeamCreateCommand teamCreateCommand);

    void addTeamMember(TeamMemberCommand teanMemberCommand);

    MyTeamDto getMyTeamDetail(Long user_id);

    List<TeamMemberDto> getTeamMembrs(Long team_id);

    TeamMemberDto getMyMembrInfo(Long user_id);

    void editTeam(TeamUpdateCommand teamUpdateCommand);


    void addTeamAgeRange(TeamAgeRangeCommand teamAgeRangeCommand);

    void addTeamActDay(TeamActDayCommand teamActDayCommand);

    void addTeamActTime(TeamActTimeCommand teamActTimeCommand);

    void deleteTeamAgeRange(Long team_id);

    void deleteTeamActDay(Long team_id);

    void deleteTeamActTime(Long team_id);
}