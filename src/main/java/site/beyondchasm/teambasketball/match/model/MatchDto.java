package site.beyondchasm.teambasketball.match.model;

import lombok.Data;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.court.model.CourtDto;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

import java.util.Date;
import java.util.List;

@Data
public class MatchDto {
    private int match_id;
    private String match_type;
    private String match_detail_type;
    private CourtDto court;
    private String region_code;
    private Date match_date;
    private Date start_time; // 매치 시작 시간
    private Date end_time; // 매치 종료 시간
    private int join_fee;
    private List<MatchMemberDto> members;
    private Date created_at;
    private Date updated_at;
    private String description; // 매치 설명
    private String status; // 매치 상태
    private int max_member_count; // 최대 참여 인원
    private int host_user_id; // 매치 주최자 ID
}