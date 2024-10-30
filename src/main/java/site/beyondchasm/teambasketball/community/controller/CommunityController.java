package site.beyondchasm.teambasketball.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "모든 채널 목록 조회", description = "커뮤니티 내 모든 채널 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "채널 목록 조회 성공")
  })
  @GetMapping("/channels")
  public ResponseEntity<List<ChannelDto>> getChannels() {
    List<ChannelDto> channels = communityService.getChannels();
    return ResponseEntity.ok(channels);
  }

  @Operation(summary = "피드 목록 조회", description = "필터 조건에 맞는 피드 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "피드 목록 조회 성공")
  })
  @GetMapping("/feeds")
  public PaginationResult<FeedDto> getFeeds(FeedFilterCommand filterCommand) {
    filterCommand.setLoginedUserId(getUserId());
    return communityService.getFeedsByChannel(filterCommand);
  }

  @Operation(summary = "팀 피드 목록 조회", description = "팀의 피드 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "팀 피드 목록 조회 성공")
  })
  @GetMapping("/teamFeeds")
  public PaginationResult<FeedDto> getTeamFeeds(FeedFilterCommand filterCommand) {
    filterCommand.setLoginedUserId(getUserId());
    return communityService.getFeedsByTeam(filterCommand);
  }

  @Operation(summary = "특정 피드 조회", description = "피드 ID를 사용해 특정 피드를 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "피드 조회 성공"),
      @ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음")
  })
  @GetMapping("/feeds/{feed_id}")
  public ResponseEntity<FeedDto> getFeed(@PathVariable("feed_id") Long feedId) {
    FeedDto feed = communityService.getFeedById(feedId, getUserId());
    return feed != null ? ResponseEntity.ok(feed) : ResponseEntity.notFound().build();
  }

  @Operation(summary = "새로운 피드 생성", description = "새로운 피드를 생성합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "피드 생성 성공"),
      @ApiResponse(responseCode = "500", description = "피드 생성 실패")
  })
  @PostMapping("/feeds")
  public ResponseEntity<FeedDto> createFeed(@RequestBody FeedCommand feedCommand) {
    FeedDto feedDto = mapToDto(feedCommand);
    feedDto.setCreatedAt(new Date());
    FeedDto feed = communityService.createFeed(feedDto, getUserId());
    return feed != null ? ResponseEntity.status(201).body(feed)
        : ResponseEntity.status(500).build();
  }

  @Operation(summary = "피드 수정", description = "기존 피드를 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "피드 수정 성공"),
      @ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음")
  })
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

  @Operation(summary = "피드 삭제", description = "피드를 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "피드 삭제 성공"),
      @ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음")
  })
  @DeleteMapping("/feeds/{feed_id}")
  public ResponseEntity<Void> deleteFeed(@PathVariable("feed_id") Long feedId) {
    FeedDto feed = communityService.getFeedById(feedId, getUserId());
    if (feed != null && communityService.deleteFeed(feedId)) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @Operation(summary = "특정 피드의 이미지 목록 조회", description = "특정 피드에 포함된 이미지 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "이미지 목록 조회 성공")
  })
  @GetMapping("/feeds/{feed_id}/images")
  public ResponseEntity<List<FeedImageDto>> getFeedImages(@PathVariable("feed_id") Long feedId) {
    List<FeedImageDto> feedImages = communityService.getFeedImages(feedId);
    return feedImages != null ? ResponseEntity.ok(feedImages) : ResponseEntity.status(500).build();
  }

  @Operation(summary = "피드 이미지 생성", description = "특정 피드에 이미지를 추가합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "이미지 생성 성공"),
      @ApiResponse(responseCode = "500", description = "이미지 생성 실패")
  })
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

  @Operation(summary = "댓글 목록 조회", description = "특정 피드의 댓글 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공")
  })
  @GetMapping("/feeds/{feed_id}/comments")
  public ResponseEntity<List<CommentDto>> getComments(@PathVariable("feed_id") Long feedId) {
    List<CommentDto> comments = communityService.getCommentsByFeed(feedId);
    return ResponseEntity.ok(comments);
  }

  @Operation(summary = "댓글 생성", description = "특정 피드에 댓글을 추가합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "댓글 생성 성공"),
      @ApiResponse(responseCode = "500", description = "댓글 생성 실패")
  })
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

  @Operation(summary = "댓글 수정", description = "기존 댓글을 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
      @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
  })
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

  @Operation(summary = "댓글 삭제", description = "기존 댓글을 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
      @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
  })
  @DeleteMapping("/comments/{comment_id}")
  public ResponseEntity<Void> deleteComment(@PathVariable("comment_id") Long commentId) {
    int result = communityService.deleteComment(commentId);
    return result > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @Operation(summary = "피드 리액션 추가 및 수정", description = "피드에 리액션을 추가하거나 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리액션 업데이트 성공"),
      @ApiResponse(responseCode = "500", description = "리액션 업데이트 실패")
  })
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

  @Operation(summary = "피드 리액션 삭제", description = "피드에서 리액션을 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리액션 삭제 성공"),
      @ApiResponse(responseCode = "500", description = "리액션 삭제 실패")
  })
  @DeleteMapping("/feeds/{feed_id}/reactions")
  public ResponseEntity<String> deleteFeedReaction(@PathVariable("feed_id") Long feedId) {
    FeedReactionDto feedReactionDto = new FeedReactionDto();
    feedReactionDto.setFeedId(feedId);
    feedReactionDto.setUserId(getUserId());

    int result = communityService.deleteFeedReaction(feedReactionDto);
    return result > 0 ? ResponseEntity.ok("Reaction deleted successfully.")
        : ResponseEntity.status(500).body("Failed to delete reaction.");
  }

  @Operation(summary = "피드 조회수 증가", description = "피드의 조회수를 증가시킵니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "조회수 증가 성공")
  })
  @PutMapping("/feeds/{feed_id}/views")
  public ResponseEntity<String> viewFeed(@PathVariable("feed_id") Long feedId) {
    communityService.addFeedView(feedId, getUserId());
    return ResponseEntity.ok("Feed view recorded.");
  }

  @Operation(summary = "댓글 좋아요", description = "특정 댓글에 좋아요를 추가합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "댓글 좋아요 성공"),
      @ApiResponse(responseCode = "500", description = "댓글 좋아요 실패")
  })
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

  @Operation(summary = "댓글 좋아요 취소", description = "댓글의 좋아요를 취소합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "댓글 좋아요 취소 성공"),
      @ApiResponse(responseCode = "500", description = "댓글 좋아요 취소 실패")
  })
  @DeleteMapping("/comments/{comment_id}/likes")
  public ResponseEntity<String> unlikeComment(@PathVariable("comment_id") Long commentId) {
    CommentLikeDto commentLikeDto = new CommentLikeDto();
    commentLikeDto.setCommentId(commentId);
    commentLikeDto.setUserId(getUserId());

    int result = communityService.unlikeComment(commentLikeDto);
    return result > 0 ? ResponseEntity.ok("Comment unliked successfully.")
        : ResponseEntity.status(500).body("Failed to unlike comment.");
  }

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
