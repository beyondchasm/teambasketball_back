package site.beyondchasm.teambasketball.team.command;

import java.util.List;
import lombok.Data;

@Data
public class TeamPhotoCommand {

  private long teamId;
  private List<String> imagePath;
}
