package site.beyondchasm.teambasketball.match.command;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class MatchFilterCommand {

  final List<String> matchType;
  final LocalDate selectDate;
  final String regionCode;
  final String order;
}
