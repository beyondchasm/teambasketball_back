package site.beyondchasm.teambasketball.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.community.command.FeedFilterCommand;
import site.beyondchasm.teambasketball.community.model.ChannelDto;
import site.beyondchasm.teambasketball.community.model.CommentDto;
import site.beyondchasm.teambasketball.community.model.CommentLikeDto;
import site.beyondchasm.teambasketball.community.model.FeedDto;
import site.beyondchasm.teambasketball.community.model.FeedImageDto;
import site.beyondchasm.teambasketball.community.model.FeedLikeDto;
import site.beyondchasm.teambasketball.community.model.FeedViewDto;

@Mapper
public interface CommunityMapper {
	// 피드 관련 메소드

	long getFeedListCount(@Param("filter") FeedFilterCommand filter);

	List<FeedDto> getFeedsByChannel(@Param("filter") FeedFilterCommand filter, @Param("offset") int offset,
			@Param("limit") int limit);

	List<ChannelDto> getChannels();

	FeedDto getFeedById(@Param("feed_id") long feed_id);

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
	int likeFeed(FeedLikeDto feedLikeDto);

	int unlikeFeed(FeedLikeDto feedLikeDto);

	int countFeedLikes(@Param("feed_id") long feed_id);

	// 피드 조회 관련 메소드
	int addFeedView(FeedViewDto feedViewDto);

	int hasUserViewedFeed(@Param("feed_id") long feed_id, @Param("user_id") long user_id);

	int incrementViewCount(@Param("feed_id") long feed_id);

	int createFeedImage(FeedImageDto feedImageDto);

	List<FeedImageDto> getFeedImages(long feed_id);

}