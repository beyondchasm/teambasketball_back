package site.beyondchasm.teambasketball.match.command;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class MatchUpdateCommand {
    private long match_id;
    private String match_type;
    private String match_detail_type;
    private long court_id;
    private String region_code;
    private LocalDate match_date;
    private LocalDate start_time; // 매치 시작 시간
    private LocalDate end_time; // 매치 종료 시간
    private int join_fee;
    private String description; // 매치 설명
    private int max_member_count; // 최대 참여 인원
}