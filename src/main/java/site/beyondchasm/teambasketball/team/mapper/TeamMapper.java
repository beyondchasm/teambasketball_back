package site.beyondchasm.teambasketball.team.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.team.command.TeamCommand;
import site.beyondchasm.teambasketball.team.command.TeamFilterCommand;
import site.beyondchasm.teambasketball.team.command.TeamMemberCommand;
import site.beyondchasm.teambasketball.team.command.TeamPhotoCommand;
import site.beyondchasm.teambasketball.team.command.TeamScheduleInfoCommand;
import site.beyondchasm.teambasketball.team.model.TeamDto;
import site.beyondchasm.teambasketball.team.model.TeamMemberDto;
import site.beyondchasm.teambasketball.team.model.TeamPhotoDto;
import site.beyondchasm.teambasketball.team.model.TeamScheduleInfoDto;

@Mapper
public interface TeamMapper {

	long getTeamListCount(@Param("filter") TeamFilterCommand filter);

	List<TeamDto> getTeamList(@Param("filter") TeamFilterCommand filter);

	TeamDto getTeamDetail(Long team_id);

	void createTeam(TeamCommand teamCommand);

	void addTeamMember(TeamMemberCommand teanMemberCommand);

	List<TeamDto> getMyTeamList(Long user_id);

	List<TeamMemberDto> getTeamMemberList(Long team_id);

	void editTeam(TeamCommand teamCommand);

	void addTeamSchedule(TeamScheduleInfoCommand command);

	List<TeamScheduleInfoDto> getTeamScheduleList(Long team_id);

	TeamScheduleInfoDto getTeamScheduleDetail(Long team_id, Long seq);

	void addTeamPhotos(TeamPhotoCommand command);

	TeamPhotoDto getTeamPhotoDetail(Long team_id, Long seq);

	List<TeamPhotoDto> getTeamPhotosList(Long team_id);

	void deleteTeamPhotos(Long team_id);

}