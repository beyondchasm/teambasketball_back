package site.beyondchasm.teambasketball.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.community.command.FeedFilterCommand;
import site.beyondchasm.teambasketball.community.model.*;

@Mapper
public interface CommunityMapper {
    // 피드 관련 메소드

    long getFeedListCount(@Param("filter") FeedFilterCommand filter);

    List<FeedDto> getFeedsByChannel(@Param("filter") FeedFilterCommand filter, @Param("offset") int offset,
                                    @Param("limit") int limit);
    long getFeedByTeamListCount(@Param("filter") FeedFilterCommand filter);

    List<FeedDto> getFeedsByTeam(@Param("filter") FeedFilterCommand filter, @Param("offset") int offset,
                                    @Param("limit") int limit);

    List<ChannelDto> getChannels();

    FeedDto getFeedById(@Param("feed_id") long feed_id, @Param("logined_user_id") long logined_user_id);

    int createFeed(FeedDto communityDto);

    int updateFeed(FeedDto communityDto);

    int deleteFeed(@Param("feed_id") long feed_id);

    // 댓글 관련 메소드
    List<CommentDto> getCommentsByFeed(@Param("feed_id") long feed_id);

    CommentDto getCommentById(@Param("comment_id") long comment_id);

    int createComment(CommentDto commentDto);

    int updateComment(CommentDto commentDto);

    int deleteComment(@Param("comment_id") long comment_id);

    // 댓글 좋아요 관련 메소드
    int likeComment(CommentLikeDto commentLikeDto);

    int unlikeComment(CommentLikeDto commentLikeDto);

    int countCommentLikes(@Param("comment_id") long comment_id);

    // 피드 좋아요 관련 메소드
    int addOrUpdateReaction(FeedReactionDto feedLikeDto);


    int countFeedLikes(@Param("feed_id") long feed_id);

    // 피드 조회 관련 메소드
    int addFeedView(FeedViewDto feedViewDto);

    int hasUserViewedFeed(@Param("feed_id") long feed_id, @Param("user_id") long user_id);

    int incrementViewCount(@Param("feed_id") long feed_id);

    int createFeedImage(FeedImageDto feedImageDto);

    List<FeedImageDto> getFeedImages(long feed_id);

    int deleteFeedReaction(FeedReactionDto feedReactionDto);

    void deleteFeedAllReaction(long feed_id);

    void deleteFeedAllView(long feed_id);

    int deleteFeedAllImages(long feed_id);

    void deleteFeedAllComment(long feed_id);
}
