package com.tofit.mvc.model.dao;

import java.util.List;

import com.tofit.mvc.model.dto.VideoReview;

public interface VideoReviewDao {

   // 댓글 전체 조회
   List<VideoReview> selectAll(String videoId);

   // 댓글 등록
   int insertReview(VideoReview review);

   // 댓글 수정
   int updateReview(VideoReview review);

   // 댓글 삭제
   int deleteReview(int reviewId);

}