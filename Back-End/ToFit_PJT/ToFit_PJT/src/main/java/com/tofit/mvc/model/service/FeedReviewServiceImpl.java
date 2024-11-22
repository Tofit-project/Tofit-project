package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.FeedReviewDao;
import com.tofit.mvc.model.dto.FeedReview;

@Service
public class FeedReviewServiceImpl implements FeedReviewService{
	
	private final FeedReviewDao feedReviewDao;
	
	public FeedReviewServiceImpl(FeedReviewDao feedReviewDao) {
		this.feedReviewDao = feedReviewDao;
	}

	// 피드 댓글 전체 조회
	@Override
	public List<FeedReview> getReviewList(int feedId) {
		return feedReviewDao.selectAll(feedId);
	}

	// 피드 댓글 등록
	@Override
	public boolean writeReview(FeedReview review) {
		int result = feedReviewDao.insertReview(review);
		return result == 1;
	}

	// 피드 댓글 수정
	@Override
	public boolean modifyReview(FeedReview review) {
		int result = feedReviewDao.updateReview(review);
		return result == 1;
	}

	// 피드 댓글 삭제
	@Override
	public boolean removeReview(int reviewId) {
		int result = feedReviewDao.deleteReview(reviewId);
		return result == 1;
	}

}
