package site.beyondchasm.teambasketball.match.command;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class MatchFilterCommand {

  final List<String> match_type;
  final LocalDate select_date;
  final String region_code;
  final String order;
}
