package site.beyondchasm.teambasketball.community.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.beyondchasm.teambasketball.common.domain.PaginationResult;
import site.beyondchasm.teambasketball.community.command.FeedFilterCommand;
import site.beyondchasm.teambasketball.community.mapper.CommunityMapper;
import site.beyondchasm.teambasketball.community.model.ChannelDto;
import site.beyondchasm.teambasketball.community.model.CommentDto;
import site.beyondchasm.teambasketball.community.model.CommentLikeDto;
import site.beyondchasm.teambasketball.community.model.FeedDto;
import site.beyondchasm.teambasketball.community.model.FeedImageDto;
import site.beyondchasm.teambasketball.community.model.FeedLikeDto;
import site.beyondchasm.teambasketball.community.model.FeedViewDto;

@Service
public class CommunityService {

    private CommunityMapper communityMapper;

    public List<ChannelDto> getChannels() {
        return communityMapper.getChannels();
    }

    // 피드 관련 메소드
    public PaginationResult<FeedDto> getFeedsByChannel(FeedFilterCommand filterCommand) {
        int offset = filterCommand.getPage() * filterCommand.getSize();
        List<FeedDto> feeds = communityMapper.getFeedsByChannel(filterCommand, offset, filterCommand.getSize());
        long totalElements = communityMapper.getFeedListCount(filterCommand); // 전체 요소 개수 조회

        PaginationResult<FeedDto> result = new PaginationResult<>();
        result.setData(feeds);
        result.setPage(filterCommand.getPage());
        result.setPageSize(filterCommand.getSize());
        result.setTotalElements(totalElements);

        return result;
    }

    public FeedDto getFeedById(long feed_id) {
        return communityMapper.getFeedById(feed_id);
    }

    public FeedDto createFeed(FeedDto feedDto) {
        communityMapper.createFeed(feedDto);
        long generatedFeedId = feedDto.getFeed_id();

        return getFeedById(generatedFeedId);
    }

    public int updateFeed(FeedDto feedDto) {
        return communityMapper.updateFeed(feedDto);
    }

    public int deleteFeed(long feed_id) {
        return communityMapper.deleteFeed(feed_id);
    }

    public int createFeedImage(FeedImageDto feedImageDto) {
        return communityMapper.createFeedImage(feedImageDto);
    }

    public List<FeedImageDto> getFeedImages(long feed_id) {
        return communityMapper.getFeedImages(feed_id);
    }

    // 댓글 관련 메소드
    public List<CommentDto> getCommentsByFeed(long feed_id) {
        return communityMapper.getCommentsByFeed(feed_id);
    }

    public CommentDto getCommentById(long comment_id) {
        return communityMapper.getCommentById(comment_id);
    }

    public int createComment(CommentDto commentDto) {
        return communityMapper.createComment(commentDto);
    }

    public int updateComment(CommentDto commentDto) {
        return communityMapper.updateComment(commentDto);
    }

    public int deleteComment(long comment_id) {
        return communityMapper.deleteComment(comment_id);
    }

    // 댓글 좋아요 관련 메소드
    public int likeComment(CommentLikeDto commentLikeDto) {
        return communityMapper.likeComment(commentLikeDto);
    }

    public int unlikeComment(CommentLikeDto commentLikeDto) {
        return communityMapper.unlikeComment(commentLikeDto);
    }

    public int countCommentLikes(long comment_id) {
        return communityMapper.countCommentLikes(comment_id);
    }

    // 피드 좋아요 관련 메소드
    public int likeFeed(FeedLikeDto feedLikeDto) {
        return communityMapper.likeFeed(feedLikeDto);
    }

    public int unlikeFeed(FeedLikeDto feedLikeDto) {
        return communityMapper.unlikeFeed(feedLikeDto);
    }

    public int countFeedLikes(long feed_id) {
        return communityMapper.countFeedLikes(feed_id);
    }

    // 피드 조회 관련 메소드
    public void addFeedView(long feed_id, long user_id) {
        // 사용자가 이미 조회했는지 확인
        int count = communityMapper.hasUserViewedFeed(feed_id, user_id);
        if (count == 0) {
            // 조회 기록 추가
            FeedViewDto feedViewDto = new FeedViewDto();
            feedViewDto.setFeed_id(feed_id);
            feedViewDto.setUser_id(user_id);
            feedViewDto.setCreated_at(new Date());

            communityMapper.addFeedView(feedViewDto);
            communityMapper.incrementViewCount(feed_id);
        }
    }

}
