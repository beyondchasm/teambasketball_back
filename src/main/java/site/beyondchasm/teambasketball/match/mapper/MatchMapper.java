package site.beyondchasm.teambasketball.match.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.beyondchasm.teambasketball.match.command.*;
import site.beyondchasm.teambasketball.match.model.MatchDto;
import site.beyondchasm.teambasketball.match.model.MatchMemberDto;

import java.util.List;

@Mapper
public interface MatchMapper {

  /**
   * 필터 조건에 맞는 매치의 개수를 조회합니다.
   *
   * @param filter 매치 필터 조건
   * @return 조회된 매치의 개수
   */
  long getMatchListCount(@Param("filter") MatchFilterCommand filter);

  /**
   * 필터 조건에 맞는 매치 목록을 조회합니다.
   *
   * @param filter 매치 필터 조건
   * @return 조회된 매치 목록
   */
  List<MatchDto> getMatchList(@Param("filter") MatchFilterCommand filter);

  /**
   * 특정 매치의 상세 정보를 조회합니다.
   *
   * @param matchId 매치 ID
   * @return 조회된 매치 상세 정보
   */
  MatchDto getMatchDetail(Long matchId);

  /**
   * 새로운 매치를 생성합니다.
   *
   * @param matchCreateCommand 생성할 매치 정보
   */
  void createMatch(MatchCreateCommand matchCreateCommand);

  /**
   * 매치에 멤버를 추가합니다.
   *
   * @param matchMemberCommand 추가할 매치 멤버 정보
   */
  void addMatchMember(MatchMemberCommand matchMemberCommand);

  /**
   * 특정 매치에 소속된 멤버 목록을 조회합니다.
   *
   * @param matchId 매치 ID
   * @return 조회된 매치 멤버 목록
   */
  List<MatchMemberDto> getMatchMembers(Long matchId);

  /**
   * 매치 정보를 수정합니다.
   *
   * @param matchUpdateCommand 수정할 매치 정보
   */
  void editMatch(MatchUpdateCommand matchUpdateCommand);

  /**
   * 특정 매치에서 멤버를 삭제합니다.
   *
   * @param matchId 매치 ID
   * @param userId  사용자 ID
   */
  void removeMatchMember(@Param("matchId") Long matchId, @Param("userId") Long userId);

  /**
   * 매치 멤버의 상태를 변경합니다.
   *
   * @param matchId 매치 ID
   * @param userId  사용자 ID
   * @param status  변경할 상태
   */
  void updateMatchMemberStatus(@Param("matchId") Long matchId, @Param("userId") Long userId,
      @Param("status") String status);
}
