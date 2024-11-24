package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.VideoReview;
import com.tofit.mvc.model.dto.VideoReviewView;

public interface VideoReviewService {

   List<VideoReviewView> getList(String videoId);

   boolean writeReview(VideoReview review);

   boolean modifyReview(VideoReview review);

   boolean removeReview(int reviewId);

}