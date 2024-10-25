package site.beyondchasm.teambasketball.community.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.beyondchasm.teambasketball.auth.model.UserPrincipal;
import site.beyondchasm.teambasketball.common.domain.PaginationResult;
import site.beyondchasm.teambasketball.community.command.CommentCommand;
import site.beyondchasm.teambasketball.community.command.FeedCommand;
import site.beyondchasm.teambasketball.community.command.FeedFilterCommand;
import site.beyondchasm.teambasketball.community.command.FeedImageCommand;
import site.beyondchasm.teambasketball.community.model.ChannelDto;
import site.beyondchasm.teambasketball.community.model.CommentDto;
import site.beyondchasm.teambasketball.community.model.CommentLikeDto;
import site.beyondchasm.teambasketball.community.model.FeedDto;
import site.beyondchasm.teambasketball.community.model.FeedImageDto;
import site.beyondchasm.teambasketball.community.model.FeedReactionDto;
import site.beyondchasm.teambasketball.community.service.CommunityService;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

  @Autowired
  private CommunityService communityService;

  private long getUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return principal instanceof UserPrincipal ? ((UserPrincipal) principal).getId() : null;
  }

  /**
   * 채널 목록 조회 GET /api/communities/channels
   */
  @GetMapping("/channels")
  public ResponseEntity<List<ChannelDto>> getChannels() {
    List<ChannelDto> channels = communityService.getChannels();
    return ResponseEntity.ok(channels);
  }

  /**
   * 피드 목록 조회 GET /api/communities/feeds
   */
  @GetMapping("/feeds")
  public PaginationResult<FeedDto> getFeeds(FeedFilterCommand filterCommand) {
    filterCommand.setLogined_user_id(getUserId());
    return communityService.getFeedsByChannel(filterCommand);
  }

  /**
   * 특정 피드 조회 GET /api/communities/feeds/{feed_id}
   */
  @GetMapping("/feeds/{feed_id}")
  public ResponseEntity<FeedDto> getFeed(@PathVariable("feed_id") Long feedId) {
    FeedDto feed = communityService.getFeedById(feedId, getUserId());
    return feed != null ? ResponseEntity.ok(feed) : ResponseEntity.notFound().build();
  }

  /**
   * 피드 생성 POST /api/communities/feeds
   */
  @PostMapping("/feeds")
  public ResponseEntity<FeedDto> createFeed(@RequestBody FeedCommand feedCommand) {
    FeedDto feedDto = mapToDto(feedCommand);
    feedDto.setCreated_at(new Date());

    FeedDto feed = communityService.createFeed(feedDto, getUserId());
    return feed != null ? ResponseEntity.status(201).body(feed)
        : ResponseEntity.status(500).build();
  }

  /**
   * 피드 수정 PUT /api/communities/feeds/{feed_id}
   */
  @PutMapping("/feeds/{feed_id}")
  public ResponseEntity<String> updateFeed(@PathVariable("feed_id") Long feedId,
      @RequestBody FeedCommand feedCommand) {
    FeedDto feedDto = mapToDto(feedCommand);
    feedDto.setFeed_id(feedId);
    feedDto.setUpdated_at(new Date());

    int result = communityService.updateFeed(feedDto);
    return result > 0 ? ResponseEntity.ok("Feed updated successfully.")
        : ResponseEntity.notFound().build();
  }

  /**
   * 피드 삭제 DELETE /api/communities/feeds/{feed_id}
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
   * 피드 이미지 목록 조회 GET /api/communities/feeds/{feed_id}/images
   */
  @GetMapping("/feeds/{feed_id}/images")
  public ResponseEntity<List<FeedImageDto>> getFeedImages(@PathVariable("feed_id") Long feedId) {
    List<FeedImageDto> feedImages = communityService.getFeedImages(feedId);
    return feedImages != null ? ResponseEntity.ok(feedImages) : ResponseEntity.status(500).build();
  }

  /**
   * 피드 이미지 생성 POST /api/communities/feeds/{feed_id}/images
   */
  @PostMapping("/feeds/{feed_id}/images")
  public ResponseEntity<String> createFeedImage(@PathVariable("feed_id") Long feedId,
      @RequestBody FeedImageCommand feedImageCommand) {
    FeedImageDto feedImageDto = new FeedImageDto();
    feedImageDto.setFeed_id(feedId);
    feedImageDto.setSeq(feedImageCommand.getSeq());
    feedImageDto.setImage_path(feedImageCommand.getImage_path());
    feedImageDto.setIs_main(feedImageCommand.getIs_main());

    int result = communityService.createFeedImage(feedImageDto);
    return result > 0 ? ResponseEntity.status(201).body("Feed image created successfully.")
        : ResponseEntity.status(500).body("Failed to create feed image.");
  }

  /**
   * 댓글 목록 조회 GET /api/communities/feeds/{feed_id}/comments
   */
  @GetMapping("/feeds/{feed_id}/comments")
  public ResponseEntity<List<CommentDto>> getComments(@PathVariable("feed_id") Long feedId) {
    List<CommentDto> comments = communityService.getCommentsByFeed(feedId);
    return ResponseEntity.ok(comments);
  }

  /**
   * 댓글 생성 POST /api/communities/feeds/{feed_id}/comments
   */
  @PostMapping("/feeds/{feed_id}/comments")
  public ResponseEntity<String> createComment(@PathVariable("feed_id") Long feedId,
      @RequestBody CommentCommand commentCommand) {
    CommentDto commentDto = new CommentDto();
    commentDto.setFeed_id(feedId);
    commentDto.setUser_id(getUserId());
    commentDto.setContent(commentCommand.getContent());
    commentDto.setCreated_at(new Date());

    int result = communityService.createComment(commentDto);
    return result > 0 ? ResponseEntity.status(201).body("Comment created successfully.")
        : ResponseEntity.status(500).body("Failed to create comment.");
  }

  /**
   * 댓글 수정 PUT /api/communities/comments/{comment_id}
   */
  @PutMapping("/comments/{comment_id}")
  public ResponseEntity<String> updateComment(@PathVariable("comment_id") Long commentId,
      @RequestBody CommentCommand commentCommand) {
    CommentDto commentDto = new CommentDto();
    commentDto.setComment_id(commentId);
    commentDto.setContent(commentCommand.getContent());
    commentDto.setUpdated_at(new Date());

    int result = communityService.updateComment(commentDto);
    return result > 0 ? ResponseEntity.ok("Comment updated successfully.")
        : ResponseEntity.notFound().build();
  }

  /**
   * 댓글 삭제 DELETE /api/communities/comments/{comment_id}
   */
  @DeleteMapping("/comments/{comment_id}")
  public ResponseEntity<Void> deleteComment(@PathVariable("comment_id") Long commentId) {
    CommentDto comment = communityService.getCommentById(commentId);
    int result = communityService.deleteComment(commentId);
    return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  /**
   * 피드 좋아요/리액션 추가 및 수정 PUT /api/communities/feeds/{feed_id}/reactions/{type}
   */
  @PutMapping("/feeds/{feed_id}/reactions/{type}")
  public ResponseEntity<String> addOrUpdateReaction(@PathVariable("feed_id") Long feedId,
      @PathVariable("type") String type) {
    FeedReactionDto feedReactionDto = new FeedReactionDto();
    feedReactionDto.setFeed_id(feedId);
    feedReactionDto.setUser_id(getUserId());
    feedReactionDto.setType(type);
    feedReactionDto.setCreated_at(new Date());

    int result = communityService.addOrUpdateReaction(feedReactionDto);
    return result > 0 ? ResponseEntity.ok("Reaction updated successfully.")
        : ResponseEntity.status(500).body("Failed to update reaction.");
  }

  /**
   * 피드 리액션 삭제 DELETE /api/communities/feeds/{feed_id}/reactions
   */
  @DeleteMapping("/feeds/{feed_id}/reactions")
  public ResponseEntity<String> deleteFeedReaction(@PathVariable("feed_id") Long feedId) {
    FeedReactionDto feedReactionDto = new FeedReactionDto();
    feedReactionDto.setFeed_id(feedId);
    feedReactionDto.setUser_id(getUserId());

    int result = communityService.deleteFeedReaction(feedReactionDto);
    return result > 0 ? ResponseEntity.ok("Reaction deleted successfully.")
        : ResponseEntity.status(500).body("Failed to delete reaction.");
  }

  /**
   * 피드 조회수 증가 PUT /api/communities/feeds/{feed_id}/views
   */
  @PutMapping("/feeds/{feed_id}/views")
  public ResponseEntity<String> viewFeed(@PathVariable("feed_id") Long feedId) {
    communityService.addFeedView(feedId, getUserId());
    return ResponseEntity.ok("Feed view recorded.");
  }

  /**
   * 댓글 좋아요 PUT /api/communities/comments/{comment_id}/likes
   */
  @PutMapping("/comments/{comment_id}/likes")
  public ResponseEntity<String> likeComment(@PathVariable("comment_id") Long commentId) {
    CommentLikeDto commentLikeDto = new CommentLikeDto();
    commentLikeDto.setComment_id(commentId);
    commentLikeDto.setUser_id(getUserId());
    commentLikeDto.setCreated_at(new Date());

    int result = communityService.likeComment(commentLikeDto);
    return result > 0 ? ResponseEntity.ok("Comment liked successfully.")
        : ResponseEntity.status(500).body("Failed to like comment.");
  }

  /**
   * 댓글 좋아요 취소 DELETE /api/communities/comments/{comment_id}/likes
   */
  @DeleteMapping("/comments/{comment_id}/likes")
  public ResponseEntity<String> unlikeComment(@PathVariable("comment_id") Long commentId) {
    CommentLikeDto commentLikeDto = new CommentLikeDto();
    commentLikeDto.setComment_id(commentId);
    commentLikeDto.setUser_id(getUserId());

    int result = communityService.unlikeComment(commentLikeDto);
    return result > 0 ? ResponseEntity.ok("Comment unliked successfully.")
        : ResponseEntity.status(500).body("Failed to unlike comment.");
  }

  // Helper method to map FeedCommand to FeedDto
  private FeedDto mapToDto(FeedCommand command) {
    FeedDto dto = new FeedDto();
    dto.setIs_team(command.getIs_team());
    if (command.getIs_team()) {
      dto.setTeam_id(command.getTeam_id());
    } else {
      dto.setChannel_id(command.getChannel_id());
    }
    dto.setTitle(command.getTitle());
    dto.setContent_type(command.getContent_type());
    dto.setContent(command.getContent());
    dto.setUser_id(getUserId());
    return dto;
  }
}
