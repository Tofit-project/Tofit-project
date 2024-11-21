package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.VideoReview;

public interface VideoReviewService {

	List<VideoReview> getList(String videoId);

	boolean writeReview(VideoReview review);

	boolean modifyReview(VideoReview review);

	boolean removeReview(int reviewId);

}
