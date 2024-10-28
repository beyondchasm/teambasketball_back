package site.beyondchasm.teambasketball.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.community.command.FeedFilterCommand;
import site.beyondchasm.teambasketball.community.model.*;

@Mapper
public interface CommunityMapper {

  // 피드 관련 메소드

  /**
   * 필터 조건에 맞는 피드의 개수를 조회합니다.
   *
   * @param filter 피드 필터 조건
   * @return 조회된 피드의 개수
   */
  long getFeedListCount(@Param("filter") FeedFilterCommand filter);

  /**
   * 채널별 필터 조건에 맞는 피드 목록을 조회합니다.
   *
   * @param filter 피드 필터 조건
   * @param offset 페이징을 위한 시작 위치
   * @param limit  페이징을 위한 제한 수
   * @return 조회된 피드 목록
   */
  List<FeedDto> getFeedsByChannel(@Param("filter") FeedFilterCommand filter,
      @Param("offset") int offset,
      @Param("limit") int limit);

  /**
   * 팀별 필터 조건에 맞는 피드 개수를 조회합니다.
   *
   * @param filter 피드 필터 조건
   * @return 조회된 팀별 피드의 개수
   */
  long getFeedByTeamListCount(@Param("filter") FeedFilterCommand filter);

  /**
   * 팀별 필터 조건에 맞는 피드 목록을 조회합니다.
   *
   * @param filter 피드 필터 조건
   * @param offset 페이징을 위한 시작 위치
   * @param limit  페이징을 위한 제한 수
   * @return 조회된 팀별 피드 목록
   */
  List<FeedDto> getFeedsByTeam(@Param("filter") FeedFilterCommand filter,
      @Param("offset") int offset,
      @Param("limit") int limit);

  /**
   * 모든 채널 목록을 조회합니다.
   *
   * @return 조회된 채널 목록
   */
  List<ChannelDto> getChannels();

  /**
   * 특정 피드 ID로 피드 상세 정보를 조회합니다.
   *
   * @param feedId        피드 ID
   * @param loginedUserId 로그인한 사용자 ID
   * @return 조회된 피드 상세 정보
   */
  FeedDto getFeedById(@Param("feedId") long feedId,
      @Param("loginedUserId") long loginedUserId);

  /**
   * 새로운 피드를 생성합니다.
   *
   * @param feedDto 생성할 피드 정보
   * @return 생성 결과 (1: 성공, 0: 실패)
   */
  int createFeed(FeedDto feedDto);

  /**
   * 피드 정보를 업데이트합니다.
   *
   * @param feedDto 업데이트할 피드 정보
   * @return 업데이트 결과 (1: 성공, 0: 실패)
   */
  int updateFeed(FeedDto feedDto);

  /**
   * 특정 피드를 삭제합니다.
   *
   * @param feedId 피드 ID
   * @return 삭제 결과 (1: 성공, 0: 실패)
   */
  int deleteFeed(@Param("feedId") long feedId);

  // 댓글 관련 메소드

  /**
   * 특정 피드의 댓글 목록을 조회합니다.
   *
   * @param feedId 피드 ID
   * @return 조회된 댓글 목록
   */
  List<CommentDto> getCommentsByFeed(@Param("feedId") long feedId);

  /**
   * 특정 댓글 ID로 댓글 상세 정보를 조회합니다.
   *
   * @param commentId 댓글 ID
   * @return 조회된 댓글 상세 정보
   */
  CommentDto getCommentById(@Param("commentId") long commentId);

  /**
   * 새로운 댓글을 생성합니다.
   *
   * @param commentDto 생성할 댓글 정보
   * @return 생성 결과 (1: 성공, 0: 실패)
   */
  int createComment(CommentDto commentDto);

  /**
   * 댓글 정보를 업데이트합니다.
   *
   * @param commentDto 업데이트할 댓글 정보
   * @return 업데이트 결과 (1: 성공, 0: 실패)
   */
  int updateComment(CommentDto commentDto);

  /**
   * 특정 댓글을 삭제합니다.
   *
   * @param commentId 댓글 ID
   * @return 삭제 결과 (1: 성공, 0: 실패)
   */
  int deleteComment(@Param("commentId") long commentId);

  // 댓글 좋아요 관련 메소드

  /**
   * 댓글에 좋아요를 추가합니다.
   *
   * @param commentLikeDto 추가할 좋아요 정보
   * @return 추가 결과 (1: 성공, 0: 실패)
   */
  int likeComment(CommentLikeDto commentLikeDto);

  /**
   * 댓글에 대한 좋아요를 취소합니다.
   *
   * @param commentLikeDto 취소할 좋아요 정보
   * @return 취소 결과 (1: 성공, 0: 실패)
   */
  int unlikeComment(CommentLikeDto commentLikeDto);

  /**
   * 특정 댓글의 좋아요 개수를 조회합니다.
   *
   * @param commentId 댓글 ID
   * @return 조회된 좋아요 개수
   */
  int countCommentLikes(@Param("commentId") long commentId);

  // 피드 좋아요 관련 메소드

  /**
   * 피드에 좋아요 또는 싫어요를 추가하거나 업데이트합니다.
   *
   * @param feedReactionDto 좋아요 또는 싫어요 정보
   * @return 추가/업데이트 결과 (1: 성공, 0: 실패)
   */
  int addOrUpdateReaction(FeedReactionDto feedReactionDto);

  /**
   * 특정 피드의 좋아요 개수를 조회합니다.
   *
   * @param feedId 피드 ID
   * @return 조회된 좋아요 개수
   */
  int countFeedLikes(@Param("feedId") long feedId);

  // 피드 조회 관련 메소드

  /**
   * 피드 조회 기록을 추가합니다.
   *
   * @param feedViewDto 조회 기록 정보
   * @return 추가 결과 (1: 성공, 0: 실패)
   */
  int addFeedView(FeedViewDto feedViewDto);

  /**
   * 사용자가 이미 특정 피드를 조회했는지 확인합니다.
   *
   * @param feedId 피드 ID
   * @param userId 사용자 ID
   * @return 조회 여부 (1: 조회함, 0: 조회하지 않음)
   */
  int hasUserViewedFeed(@Param("feedId") long feedId, @Param("userId") long userId);

  /**
   * 특정 피드의 조회수를 증가시킵니다.
   *
   * @param feedId 피드 ID
   * @return 증가 결과 (1: 성공, 0: 실패)
   */
  int incrementViewCount(@Param("feedId") long feedId);

  // 피드 이미지 관련 메소드

  /**
   * 피드에 새로운 이미지를 추가합니다.
   *
   * @param feedImageDto 추가할 이미지 정보
   * @return 추가 결과 (1: 성공, 0: 실패)
   */
  int createFeedImage(FeedImageDto feedImageDto);

  /**
   * 특정 피드의 이미지 목록을 조회합니다.
   *
   * @param feedId 피드 ID
   * @return 조회된 이미지 목록
   */
  List<FeedImageDto> getFeedImages(long feedId);

  // 피드와 관련된 모든 반응 및 조회 기록 삭제 메소드

  /**
   * 피드에 대한 특정 반응을 삭제합니다.
   *
   * @param feedReactionDto 삭제할 반응 정보
   * @return 삭제 결과 (1: 성공, 0: 실패)
   */
  int deleteFeedReaction(FeedReactionDto feedReactionDto);

  /**
   * 피드와 관련된 모든 반응을 삭제합니다.
   *
   * @param feedId 피드 ID
   */
  void deleteFeedAllReaction(long feedId);

  /**
   * 피드와 관련된 모든 조회 기록을 삭제합니다.
   *
   * @param feedId 피드 ID
   */
  void deleteFeedAllView(long feedId);

  /**
   * 피드와 관련된 모든 이미지를 삭제합니다.
   *
   * @param feedId 피드 ID
   * @return 삭제된 이미지 수
   */
  int deleteFeedAllImages(long feedId);

  /**
   * 피드에 작성된 모든 댓글을 삭제합니다.
   *
   * @param feedId 피드 ID
   */
  void deleteFeedAllComment(long feedId);
}
