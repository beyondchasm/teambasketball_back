package site.beyondchasm.teambasketball.court.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CourtImageDto {

  private long courtId;
  private long seq;
  private String imagePath;
  private String isMain;

}