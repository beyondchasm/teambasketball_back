package site.beyondchasm.teambasketball.community.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.beyondchasm.teambasketball.common.domain.PaginationResult;
import site.beyondchasm.teambasketball.community.command.FeedFilterCommand;
import site.beyondchasm.teambasketball.community.mapper.CommunityMapper;
import site.beyondchasm.teambasketball.community.model.*;

@Service
public class CommunityService {

  @Autowired
  private CommunityMapper communityMapper;

  /**
   * 모든 채널 목록을 조회합니다.
   *
   * @return 채널 목록
   */
  public List<ChannelDto> getChannels() {
    return communityMapper.getChannels();
  }

  // 피드 관련 메소드

  /**
   * 채널별 필터 조건에 맞는 피드 목록을 페이징 처리하여 조회합니다.
   *
   * @param filterCommand 피드 필터 조건
   * @return 페이징된 피드 목록
   */
  public PaginationResult<FeedDto> getFeedsByChannel(FeedFilterCommand filterCommand) {
    int offset = filterCommand.getPage() * filterCommand.getSize();
    List<FeedDto> feeds = communityMapper.getFeedsByChannel(filterCommand, offset,
        filterCommand.getSize());
    long totalElements = communityMapper.getFeedListCount(filterCommand);

    PaginationResult<FeedDto> result = new PaginationResult<>();
    result.setData(feeds);
    result.setPage(filterCommand.getPage());
    result.setPageSize(filterCommand.getSize());
    result.setTotalElements(totalElements);

    return result;
  }

  /**
   * 팀별 필터 조건에 맞는 피드 목록을 페이징 처리하여 조회합니다.
   *
   * @param filterCommand 피드 필터 조건
   * @return 페이징된 팀별 피드 목록
   */
  public PaginationResult<FeedDto> getFeedsByTeam(FeedFilterCommand filterCommand) {
    int offset = filterCommand.getPage() * filterCommand.getSize();
    List<FeedDto> feeds = communityMapper.getFeedsByTeam(filterCommand, offset,
        filterCommand.getSize());
    long totalElements = communityMapper.getFeedByTeamListCount(filterCommand);

    PaginationResult<FeedDto> result = new PaginationResult<>();
    result.setData(feeds);
    result.setPage(filterCommand.getPage());
    result.setPageSize(filterCommand.getSize());
    result.setTotalElements(totalElements);

    return result;
  }

  /**
   * 피드 ID를 통해 특정 피드를 조회합니다. 조회 기록을 추가하며 조회수를 증가시킵니다.
   *
   * @param feedId        피드 ID
   * @param loginedUserId 로그인한 사용자 ID
   * @return 조회된 피드 정보
   */
  public FeedDto getFeedById(long feedId, long loginedUserId) {
    addFeedView(feedId, loginedUserId);
    return communityMapper.getFeedById(feedId, loginedUserId);
  }

  /**
   * 새로운 피드를 생성합니다.
   *
   * @param feedDto       생성할 피드 정보
   * @param loginedUserId 로그인한 사용자 ID
   * @return 생성된 피드 정보
   */
  public FeedDto createFeed(FeedDto feedDto, long loginedUserId) {
    communityMapper.createFeed(feedDto);
    long generatedFeedId = feedDto.getFeedId();
    return getFeedById(generatedFeedId, loginedUserId);
  }

  /**
   * 피드 정보를 업데이트합니다.
   *
   * @param feedDto 업데이트할 피드 정보
   * @return 업데이트 결과 (1: 성공, 0: 실패)
   */
  public int updateFeed(FeedDto feedDto) {
    return communityMapper.updateFeed(feedDto);
  }

  /**
   * 특정 피드를 삭제하고 관련 데이터를 모두 삭제합니다.
   *
   * @param feedId 피드 ID
   * @return 삭제 성공 여부
   */
  public boolean deleteFeed(long feedId) {
    boolean rtnVal = true;
    try {
      communityMapper.deleteFeed(feedId);
      communityMapper.deleteFeedAllReaction(feedId);
      communityMapper.deleteFeedAllView(feedId);
      communityMapper.deleteFeedAllImages(feedId);
      communityMapper.deleteFeedAllComment(feedId);
    } catch (Exception e) {
      rtnVal = false;
    }
    return rtnVal;
  }

  /**
   * 새로운 피드 이미지를 추가합니다.
   *
   * @param feedImageDto 추가할 이미지 정보
   * @return 추가 결과
   */
  public int createFeedImage(FeedImageDto feedImageDto) {
    return communityMapper.createFeedImage(feedImageDto);
  }

  /**
   * 특정 피드의 모든 이미지를 삭제합니다.
   *
   * @param feedId 피드 ID
   * @return 삭제된 이미지 수
   */
  public int deleteFeedAllImages(long feedId) {
    return communityMapper.deleteFeedAllImages(feedId);
  }

  /**
   * 특정 피드의 이미지 목록을 조회합니다.
   *
   * @param feedId 피드 ID
   * @return 이미지 목록
   */
  public List<FeedImageDto> getFeedImages(long feedId) {
    return communityMapper.getFeedImages(feedId);
  }

  // 댓글 관련 메소드

  /**
   * 특정 피드의 댓글 목록을 조회합니다.
   *
   * @param feedId 피드 ID
   * @return 댓글 목록
   */
  public List<CommentDto> getCommentsByFeed(long feedId) {
    return communityMapper.getCommentsByFeed(feedId);
  }

  /**
   * 특정 댓글 ID로 댓글을 조회합니다.
   *
   * @param commentId 댓글 ID
   * @return 댓글 정보
   */
  public CommentDto getCommentById(long commentId) {
    return communityMapper.getCommentById(commentId);
  }

  /**
   * 새로운 댓글을 생성합니다.
   *
   * @param commentDto 생성할 댓글 정보
   * @return 생성 결과
   */
  public int createComment(CommentDto commentDto) {
    return communityMapper.createComment(commentDto);
  }

  /**
   * 댓글을 업데이트합니다.
   *
   * @param commentDto 업데이트할 댓글 정보
   * @return 업데이트 결과
   */
  public int updateComment(CommentDto commentDto) {
    return communityMapper.updateComment(commentDto);
  }

  /**
   * 특정 댓글을 삭제합니다.
   *
   * @param commentId 댓글 ID
   * @return 삭제 결과
   */
  public int deleteComment(long commentId) {
    return communityMapper.deleteComment(commentId);
  }

  // 댓글 좋아요 관련 메소드

  /**
   * 댓글에 좋아요를 추가합니다.
   *
   * @param commentLikeDto 추가할 좋아요 정보
   * @return 추가 결과
   */
  public int likeComment(CommentLikeDto commentLikeDto) {
    return communityMapper.likeComment(commentLikeDto);
  }

  /**
   * 댓글 좋아요를 취소합니다.
   *
   * @param commentLikeDto 취소할 좋아요 정보
   * @return 취소 결과
   */
  public int unlikeComment(CommentLikeDto commentLikeDto) {
    return communityMapper.unlikeComment(commentLikeDto);
  }

  /**
   * 특정 댓글의 좋아요 개수를 조회합니다.
   *
   * @param commentId 댓글 ID
   * @return 좋아요 개수
   */
  public int countCommentLikes(long commentId) {
    return communityMapper.countCommentLikes(commentId);
  }

  // 피드 좋아요 관련 메소드

  /**
   * 피드에 좋아요 또는 싫어요를 추가하거나 업데이트합니다.
   *
   * @param feedReactionDto 좋아요/싫어요 정보
   * @return 추가/업데이트 결과
   */
  public int addOrUpdateReaction(FeedReactionDto feedReactionDto) {
    communityMapper.deleteFeedReaction(feedReactionDto);
    return communityMapper.addOrUpdateReaction(feedReactionDto);
  }

  /**
   * 피드의 특정 반응을 삭제합니다.
   *
   * @param feedReactionDto 삭제할 반응 정보
   * @return 삭제 결과
   */
  public int deleteFeedReaction(FeedReactionDto feedReactionDto) {
    return communityMapper.deleteFeedReaction(feedReactionDto);
  }

  // 피드 조회 관련 메소드

  /**
   * 특정 피드에 대한 조회 기록을 추가하고 조회수를 증가시킵니다.
   *
   * @param feedId        피드 ID
   * @param loginedUserId 로그인한 사용자 ID
   */
  public void addFeedView(long feedId, long loginedUserId) {
    FeedViewDto feedViewDto = new FeedViewDto();
    feedViewDto.setFeedId(feedId);
    feedViewDto.setUserId(loginedUserId);
    feedViewDto.setCreatedAt(new Date());

    communityMapper.addFeedView(feedViewDto);
    communityMapper.incrementViewCount(feedId);
  }
}
