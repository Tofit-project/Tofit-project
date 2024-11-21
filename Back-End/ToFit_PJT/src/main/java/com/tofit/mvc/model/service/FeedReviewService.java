package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.FeedReview;

public interface FeedReviewService {

	List<FeedReview> getReviewList(int feedId);

	boolean writeReview(FeedReview review);

	boolean modifyReview(FeedReview review);

	boolean removeReview(int reviewId);

}
