package site.beyondchasm.teambasketball.community.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.beyondchasm.teambasketball.auth.model.UserPrincipal;
import site.beyondchasm.teambasketball.common.domain.PaginationResult;
import site.beyondchasm.teambasketball.community.command.*;
import site.beyondchasm.teambasketball.community.model.*;
import site.beyondchasm.teambasketball.community.service.CommunityService;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

  @Autowired
  private CommunityService communityService;

  /**
   * 현재 로그인한 사용자 ID를 가져옵니다.
   *
   * @return 사용자 ID
   */
  private long getUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return principal instanceof UserPrincipal ? ((UserPrincipal) principal).getId() : null;
  }

  /**
   * 모든 채널 목록 조회
   *
   * @return 채널 목록
   */
  @GetMapping("/channels")
  public ResponseEntity<List<ChannelDto>> getChannels() {
    List<ChannelDto> channels = communityService.getChannels();
    return ResponseEntity.ok(channels);
  }

  /**
   * 피드 목록 조회
   *
   * @param filterCommand 피드 필터 조건
   * @return 필터링된 피드 목록
   */
  @GetMapping("/feeds")
  public PaginationResult<FeedDto> getFeeds(FeedFilterCommand filterCommand) {
    filterCommand.setLoginedUserId(getUserId());
    return communityService.getFeedsByChannel(filterCommand);
  }


  /**
   * 피드 목록 조회 GET /community/feeds
   */
  @GetMapping("/teamFeeds")
  public PaginationResult<FeedDto> getTeamFeeds(FeedFilterCommand filterCommand) {
    filterCommand.setLoginedUserId(getUserId());
    // 필터 커맨드에 채널 ID 설정
    PaginationResult<FeedDto> feeds = communityService.getFeedsByTeam(filterCommand);
    return feeds;
  }

  /**
   * 특정 피드 조회
   *
   * @param feedId 조회할 피드 ID
   * @return 조회된 피드
   */
  @GetMapping("/feeds/{feed_id}")
  public ResponseEntity<FeedDto> getFeed(@PathVariable("feed_id") Long feedId) {
    FeedDto feed = communityService.getFeedById(feedId, getUserId());
    return feed != null ? ResponseEntity.ok(feed) : ResponseEntity.notFound().build();
  }

  /**
   * 새로운 피드 생성
   *
   * @param feedCommand 생성할 피드 정보
   * @return 생성된 피드
   */
  @PostMapping("/feeds")
  public ResponseEntity<FeedDto> createFeed(@RequestBody FeedCommand feedCommand) {
    FeedDto feedDto = mapToDto(feedCommand);
    feedDto.setCreatedAt(new Date());

    FeedDto feed = communityService.createFeed(feedDto, getUserId());
    return feed != null ? ResponseEntity.status(201).body(feed)
        : ResponseEntity.status(500).build();
  }

  /**
   * 피드 수정
   *
   * @param feedId      수정할 피드 ID
   * @param feedCommand 수정할 피드 정보
   * @return 수정 성공 여부
   */
  @PutMapping("/feeds/{feed_id}")
  public ResponseEntity<String> updateFeed(@PathVariable("feed_id") Long feedId,
      @RequestBody FeedCommand feedCommand) {
    FeedDto feedDto = mapToDto(feedCommand);
    feedDto.setFeedId(feedId);
    feedDto.setUpdatedAt(new Date());

    int result = communityService.updateFeed(feedDto);
    return result > 0 ? ResponseEntity.ok("Feed updated successfully.")
        : ResponseEntity.notFound().build();
  }

  /**
   * 피드 삭제
   *
   * @param feedId 삭제할 피드 ID
   * @return 삭제 성공 여부
   */
  @DeleteMapping("/feeds/{feed_id}")
  public ResponseEntity<Void> deleteFeed(@PathVariable("feed_id") Long feedId) {
    FeedDto feed = communityService.getFeedById(feedId, getUserId());
    if (feed != null && communityService.deleteFeed(feedId)) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * 특정 피드의 이미지 목록 조회
   *
   * @param feedId 조회할 피드 ID
   * @return 피드 이미지 목록
   */
  @GetMapping("/feeds/{feed_id}/images")
  public ResponseEntity<List<FeedImageDto>> getFeedImages(@PathVariable("feed_id") Long feedId) {
    List<FeedImageDto> feedImages = communityService.getFeedImages(feedId);
    return feedImages != null ? ResponseEntity.ok(feedImages) : ResponseEntity.status(500).build();
  }

  /**
   * 피드 이미지 생성
   *
   * @param feedId           피드 ID
   * @param feedImageCommand 생성할 이미지 정보
   * @return 생성 성공 여부
   */
  @PostMapping("/feeds/{feed_id}/images")
  public ResponseEntity<String> createFeedImage(@PathVariable("feed_id") Long feedId,
      @RequestBody FeedImageCommand feedImageCommand) {
    FeedImageDto feedImageDto = new FeedImageDto();
    feedImageDto.setFeedId(feedId);
    feedImageDto.setSeq(feedImageCommand.getSeq());
    feedImageDto.setImagePath(feedImageCommand.getImagePath());
    feedImageDto.setIsMain(feedImageCommand.getIsMain());

    int result = communityService.createFeedImage(feedImageDto);
    return result > 0 ? ResponseEntity.status(201).body("Feed image created successfully.")
        : ResponseEntity.status(500).body("Failed to create feed image.");
  }


  /**
   * 댓글 목록 조회
   *
   * @param feedId 조회할 피드 ID
   * @return 댓글 목록
   */
  @GetMapping("/feeds/{feed_id}/comments")
  public ResponseEntity<List<CommentDto>> getComments(@PathVariable("feed_id") Long feedId) {
    List<CommentDto> comments = communityService.getCommentsByFeed(feedId);
    return ResponseEntity.ok(comments);
  }

  /**
   * 댓글 생성
   *
   * @param feedId         피드 ID
   * @param commentCommand 생성할 댓글 정보
   * @return 생성 성공 여부
   */
  @PostMapping("/feeds/{feed_id}/comments")
  public ResponseEntity<String> createComment(@PathVariable("feed_id") Long feedId,
      @RequestBody CommentCommand commentCommand) {
    CommentDto commentDto = new CommentDto();
    commentDto.setFeedId(feedId);
    commentDto.setUserId(getUserId());
    commentDto.setContent(commentCommand.getContent());
    commentDto.setCreatedAt(new Date());

    int result = communityService.createComment(commentDto);
    return result > 0 ? ResponseEntity.status(201).body("Comment created successfully.")
        : ResponseEntity.status(500).body("Failed to create comment.");
  }

  /**
   * 댓글 수정
   *
   * @param commentId      수정할 댓글 ID
   * @param commentCommand 수정할 댓글 정보
   * @return 수정 성공 여부
   */
  @PutMapping("/comments/{comment_id}")
  public ResponseEntity<String> updateComment(@PathVariable("comment_id") Long commentId,
      @RequestBody CommentCommand commentCommand) {
    CommentDto commentDto = new CommentDto();
    commentDto.setCommentId(commentId);
    commentDto.setContent(commentCommand.getContent());
    commentDto.setUpdatedAt(new Date());

    int result = communityService.updateComment(commentDto);
    return result > 0 ? ResponseEntity.ok("Comment updated successfully.")
        : ResponseEntity.notFound().build();
  }

  /**
   * 댓글 삭제
   *
   * @param commentId 삭제할 댓글 ID
   * @return 삭제 성공 여부
   */
  @DeleteMapping("/comments/{comment_id}")
  public ResponseEntity<Void> deleteComment(@PathVariable("comment_id") Long commentId) {
    int result = communityService.deleteComment(commentId);
    return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  /**
   * 피드 리액션 추가 및 수정
   *
   * @param feedId 피드 ID
   * @param type   리액션 타입 (예: 'like', 'dislike')
   * @return 리액션 업데이트 성공 여부
   */
  @PutMapping("/feeds/{feed_id}/reactions/{type}")
  public ResponseEntity<String> addOrUpdateReaction(@PathVariable("feed_id") Long feedId,
      @PathVariable("type") String type) {
    FeedReactionDto feedReactionDto = new FeedReactionDto();
    feedReactionDto.setFeedId(feedId);
    feedReactionDto.setUserId(getUserId());
    feedReactionDto.setType(type);
    feedReactionDto.setCreatedAt(new Date());

    int result = communityService.addOrUpdateReaction(feedReactionDto);
    return result > 0 ? ResponseEntity.ok("Reaction updated successfully.")
        : ResponseEntity.status(500).body("Failed to update reaction.");
  }

  /**
   * 피드 리액션 삭제
   *
   * @param feedId 피드 ID
   * @return 삭제 성공 여부
   */
  @DeleteMapping("/feeds/{feed_id}/reactions")
  public ResponseEntity<String> deleteFeedReaction(@PathVariable("feed_id") Long feedId) {
    FeedReactionDto feedReactionDto = new FeedReactionDto();
    feedReactionDto.setFeedId(feedId);
    feedReactionDto.setUserId(getUserId());

    int result = communityService.deleteFeedReaction(feedReactionDto);
    return result > 0 ? ResponseEntity.ok("Reaction deleted successfully.")
        : ResponseEntity.status(500).body("Failed to delete reaction.");
  }

  /**
   * 피드 조회수 증가
   *
   * @param feedId 피드 ID
   * @return 조회수 증가 성공 여부
   */
  @PutMapping("/feeds/{feed_id}/views")
  public ResponseEntity<String> viewFeed(@PathVariable("feed_id") Long feedId) {
    communityService.addFeedView(feedId, getUserId());
    return ResponseEntity.ok("Feed view recorded.");
  }

  /**
   * 댓글 좋아요
   *
   * @param commentId 좋아요할 댓글 ID
   * @return 좋아요 성공 여부
   */
  @PutMapping("/comments/{comment_id}/likes")
  public ResponseEntity<String> likeComment(@PathVariable("comment_id") Long commentId) {
    CommentLikeDto commentLikeDto = new CommentLikeDto();
    commentLikeDto.setCommentId(commentId);
    commentLikeDto.setUserId(getUserId());
    commentLikeDto.setCreatedAt(new Date());

    int result = communityService.likeComment(commentLikeDto);
    return result > 0 ? ResponseEntity.ok("Comment liked successfully.")
        : ResponseEntity.status(500).body("Failed to like comment.");
  }

  /**
   * 댓글 좋아요 취소
   *
   * @param commentId 좋아요 취소할 댓글 ID
   * @return 좋아요 취소 성공 여부
   */
  @DeleteMapping("/comments/{comment_id}/likes")
  public ResponseEntity<String> unlikeComment(@PathVariable("comment_id") Long commentId) {
    CommentLikeDto commentLikeDto = new CommentLikeDto();
    commentLikeDto.setCommentId(commentId);
    commentLikeDto.setUserId(getUserId());

    int result = communityService.unlikeComment(commentLikeDto);
    return result > 0 ? ResponseEntity.ok("Comment unliked successfully.")
        : ResponseEntity.status(500).body("Failed to unlike comment.");
  }

  /**
   * FeedCommand를 FeedDto로 변환하는 헬퍼 메소드
   *
   * @param command 변환할 FeedCommand 객체
   * @return 변환된 FeedDto 객체
   */
  private FeedDto mapToDto(FeedCommand command) {
    FeedDto dto = new FeedDto();
    dto.setIsTeam(command.getIsTeam());
    if (command.getIsTeam()) {
      dto.setTeamId(command.getTeamId());
    } else {
      dto.setChannelId(command.getChannelId());
    }
    dto.setTitle(command.getTitle());
    dto.setContentType(command.getContentType());
    dto.setContent(command.getContent());
    dto.setUserId(getUserId());
    return dto;
  }
}
