package site.beyondchasm.teambasketball.team.command;

import java.time.LocalTime;

import lombok.Data;

@Data
public class TeamScheduleInfoCommand {
	private Long team_id;
	private Long court_id;
	private String day_of_week;
	private LocalTime start_time;
	private LocalTime end_time;
}