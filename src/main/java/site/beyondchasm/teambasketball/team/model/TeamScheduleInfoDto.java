package site.beyondchasm.teambasketball.team.model;

import java.time.LocalTime;

import lombok.Data;

@Data
public class TeamScheduleInfoDto {
	private Long team_id;
	private Long seq;
	private Long court_id;
	private String court_name;
	private String day_of_week;
	private LocalTime start_time;
	private LocalTime end_time;
}