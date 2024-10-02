package site.beyondchasm.teambasketball.court.command;

import java.util.List;

import lombok.Data;

@Data
public class CourtFilterCommand {

	private List<String> search_court_type;
	private String search_court_name;
	private String search_region_code;
}
