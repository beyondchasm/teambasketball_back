package site.beyondchasm.teambasketball.match.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.beyondchasm.teambasketball.match.command.*;
import site.beyondchasm.teambasketball.match.model.MatchDto;
import site.beyondchasm.teambasketball.match.model.MatchMemberDto;

import java.util.List;

@Mapper
public interface MatchMapper {

  long getMatchListCount(@Param("filter") MatchFilterCommand filter);

  List<MatchDto> getMatchList(@Param("filter") MatchFilterCommand filter);

  MatchDto getMatchDetail(Long match_id);

  void createMatch(MatchCreateCommand matchCreateCommand);

  // 매치 멤버 추가
  void addMatchMember(MatchMemberCommand matchMemberCommand);

  // 매치 멤버 목록 가져오기
  List<MatchMemberDto> getMatchMembers(Long match_id);

  // 매치 수정
  void editMatch(MatchUpdateCommand matchUpdateCommand);

  // 매치 멤버 삭제
  void removeMatchMember(@Param("match_id") Long match_id, @Param("user_id") Long user_id);

  // 매치 멤버 상태 변경
  void updateMatchMemberStatus(@Param("match_id") Long match_id, @Param("user_id") Long user_id,
      @Param("status") String status);
}

