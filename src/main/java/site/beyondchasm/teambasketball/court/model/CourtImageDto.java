package site.beyondchasm.teambasketball.court.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CourtImageDto {
    private long court_id;
    private long seq;
    private String image_path;
    private String is_main;

}