package site.beyondchasm.teambasketball.match.command;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class MatchFilterCommand {

  final List<String> matchType;
  final List<String> matchDetailType;
  final LocalDate selectDate;
  final String regionCode;
  final String order;
}
