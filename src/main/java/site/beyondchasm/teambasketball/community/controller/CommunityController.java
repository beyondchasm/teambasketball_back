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
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    private long getUserId() {
        // 현재 로그인한 사용자 정보 얻기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;
        if (principal instanceof UserPrincipal) {
            userId = ((UserPrincipal) principal).getId();
        }
        return userId;
    }

    /**
     * 채널 목록 조회 GET /community/channels
     */
    @GetMapping("/channels")
    public ResponseEntity<List<ChannelDto>> getChannelss() {
        List<ChannelDto> channels = communityService.getChannels();
        return ResponseEntity.ok(channels);
    }

    /**
     * 피드 목록 조회 GET /community/feeds
     */
    @GetMapping("/feeds")
    public PaginationResult<FeedDto> getFeeds(FeedFilterCommand filterCommand) {
        filterCommand.setLogined_user_id(getUserId());
        // 필터 커맨드에 채널 ID 설정
        PaginationResult<FeedDto> feeds = communityService.getFeedsByChannel(filterCommand);
        return feeds;
    }

    /**
     * 특정 피드 조회 GET /community/feeds/{feed_id}
     */
    @GetMapping("/feeds/{feed_id}")
    public ResponseEntity<FeedDto> getFeed(@PathVariable("feed_id") Long feed_id) {
        FeedDto feed = communityService.getFeedById(feed_id, getUserId());
        if (feed != null) {
            return ResponseEntity.ok(feed);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 피드 생성 POST /community/feeds
     */
    @PostMapping("/createFeed")
    public ResponseEntity<FeedDto> createFeed(@RequestBody FeedCommand feedCommand) {
        FeedDto feedDto = mapToDto(feedCommand);
        feedDto.setCreated_at(new Date());

        FeedDto feed = communityService.createFeed(feedDto, getUserId());
        if (feed != null) {
            return ResponseEntity.ok(feed);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 피드 생성 POST /community/feeds
     */
    @PostMapping("/createFeedImage")
    public ResponseEntity<String> createFeedImgae(@RequestBody FeedImageCommand feedImageCommand) {
        FeedImageDto feedImageDto = new FeedImageDto();
        feedImageDto.setFeed_id(feedImageCommand.getFeed_id());
        feedImageDto.setSeq(feedImageCommand.getSeq());
        feedImageDto.setImage_path(feedImageCommand.getImage_path());
        feedImageDto.setIs_main(feedImageCommand.getIs_main());

        int result = communityService.createFeedImage(feedImageDto);
        if (result > 0) {
            return ResponseEntity.status(201).body("Feed created successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to create feed.");
        }
    }

    /**
     * 피드 이미지목록
     */
    @GetMapping("/feeds/{feed_id}/images")
    public ResponseEntity<List<FeedImageDto>> getFeedImages(@PathVariable("feed_id") Long feed_id) {

        List<FeedImageDto> feedImages = communityService.getFeedImages(feed_id);
        if (feedImages != null) {
            return ResponseEntity.ok(feedImages);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 피드 수정 PUT /community/feeds/{feed_id}
     */
    @PostMapping("/updateFeed")
    public ResponseEntity<String> updateFeed(@RequestBody FeedCommand feedCommand) {
        FeedDto feedDto = mapToDto(feedCommand);
        feedDto.setChannel_id(feedCommand.getChannel_id());
        feedDto.setTitle(feedCommand.getTitle());
        feedDto.setContent(feedCommand.getContent());
        feedDto.setUpdated_at(new Date());

        int result = communityService.updateFeed(feedDto);
        if (result > 0) {
            return ResponseEntity.ok("Feed updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 피드 삭제 DELETE /community/feeds/{feed_id}
     */
    @DeleteMapping("/deleteFeed/{feed_id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("feed_id") Long feed_id) {
        // 피드가 해당 채널에 속하는지 확인
        FeedDto feed = communityService.getFeedById(feed_id, getUserId());
        if (feed != null) {
            int result = communityService.deleteFeed(feed_id);
            if (result > 0) {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 댓글 목록 조회 GET /community/feeds/{feed_id}/comments
     */
    @GetMapping("/feeds/{feed_id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("feed_id") Long feed_id) {
        List<CommentDto> comments = communityService.getCommentsByFeed(feed_id);
        return ResponseEntity.ok(comments);
    }

    /**
     * 댓글 작성 POST /community/feeds/{feed_id}/comments
     */
    @PostMapping("/createComment")
    public ResponseEntity<String> createComment(@RequestBody CommentCommand commentCommand) {
        CommentDto commentDto = new CommentDto();
        commentDto.setFeed_id(commentCommand.getFeed_id());
        commentDto.setUser_id(getUserId());
        commentDto.setContent(commentCommand.getContent());
        commentDto.setCreated_at(new Date());

        int result = communityService.createComment(commentDto);
        if (result > 0) {
            return ResponseEntity.status(201).body("Comment created successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to create comment.");
        }
    }

    /**
     * 댓글 수정 PUT /community/comments/{comment_id}
     */
    @PostMapping("/updateComment")
    public ResponseEntity<String> updateComment(@RequestBody CommentCommand commentCommand) {
        CommentDto commentDto = new CommentDto();

        commentDto.setContent(commentCommand.getContent());
        commentDto.setUpdated_at(new Date());

        int result = communityService.updateComment(commentDto);
        if (result > 0) {
            return ResponseEntity.ok("Comment updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 댓글 삭제 DELETE /community/comments/{comment_id}
     */
    @DeleteMapping("/deleteComment/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("comment_id") Long comment_id) {
        // 댓글이 해당 채널에 속하는지 확인
        CommentDto comment = communityService.getCommentById(comment_id);
        int result = communityService.deleteComment(comment_id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 피드 좋아요
     */
    @GetMapping("/feeds/{feed_id}/addOrUpdateReaction/{type}")
    public ResponseEntity<String> addOrUpdateReaction(@PathVariable("feed_id") Long feed_id, @PathVariable("type") String type) {

        FeedReactionDto feedReactionDto = new FeedReactionDto();
        feedReactionDto.setFeed_id(feed_id);
        feedReactionDto.setUser_id(getUserId());
        feedReactionDto.setType(type);
        feedReactionDto.setCreated_at(new Date());

        int result = communityService.addOrUpdateReaction(feedReactionDto);
        if (result > 0) {
            return ResponseEntity.ok("Feed liked successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to like feed.");
        }
    }

    /**
     * 피드 리액션 취소
     */
    @GetMapping("/feeds/{feed_id}/deleteFeedReaction")
    public ResponseEntity<String> deleteFeedReaction(@PathVariable("feed_id") Long feed_id) {

        FeedReactionDto feedReactionDto = new FeedReactionDto();
        feedReactionDto.setFeed_id(feed_id);
        feedReactionDto.setUser_id(getUserId());
        feedReactionDto.setCreated_at(new Date());

        int result = communityService.deleteFeedReaction(feedReactionDto);
        if (result > 0) {
            return ResponseEntity.ok("Feed liked successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to like feed.");
        }
    }


    /**
     * 피드 조회수 증가 POST /community/feeds/{feed_id}/view
     */
    @GetMapping("/feeds/{feed_id}/view")
    public ResponseEntity<String> viewFeed(@PathVariable("feed_id") Long feed_id) {
        communityService.addFeedView(feed_id, getUserId());
        return ResponseEntity.ok("Feed view recorded.");
    }

    /**
     * 댓글 좋아요 POST /community/comments/{comment_id}/like
     */
    @GetMapping("/comments/{comment_id}/like")
    public ResponseEntity<String> likeComment(@PathVariable("comment_id") Long comment_id) {
        CommentLikeDto commentLikeDto = new CommentLikeDto();
        commentLikeDto.setComment_id(comment_id);
        commentLikeDto.setUser_id(getUserId());
        commentLikeDto.setCreated_at(new Date());

        int result = communityService.likeComment(commentLikeDto);
        if (result > 0) {
            return ResponseEntity.ok("Comment liked successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to like comment.");
        }
    }

    /**
     * 댓글 좋아요 취소 POST /community/comments/{comment_id}/unlike
     */
    @GetMapping("/comments/{comment_id}/unlike")
    public ResponseEntity<String> unlikeComment(@PathVariable("comment_id") Long comment_id) {
        CommentLikeDto commentLikeDto = new CommentLikeDto();
        commentLikeDto.setComment_id(comment_id);
        commentLikeDto.setUser_id(getUserId());

        int result = communityService.unlikeComment(commentLikeDto);
        if (result > 0) {
            return ResponseEntity.ok("Comment unliked successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to unlike comment.");
        }
    }

    // Helper method to map FeedCommand to FeedDto
    private FeedDto mapToDto(FeedCommand command) {
        FeedDto dto = new FeedDto();
        dto.setChannel_id(command.getChannel_id());
        dto.setTitle(command.getTitle());
        dto.setContent_type(command.getContent_type());
        dto.setContent(command.getContent());
        dto.setUser_id(getUserId());
        // dto.setChannel_id은 컨트롤러 메소드에서 설정
        // dto.setCreated_at과 dto.setUpdated_at은 컨트롤러 메소드에서 설정
        return dto;
    }
}
