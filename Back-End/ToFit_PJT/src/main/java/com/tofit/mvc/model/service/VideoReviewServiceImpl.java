package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.VideoReviewDao;
import com.tofit.mvc.model.dto.VideoReview;
import com.tofit.mvc.model.dto.VideoReviewView;

@Service
public class VideoReviewServiceImpl implements VideoReviewService{
   
   private final VideoReviewDao videoReviewDao;
   
   public VideoReviewServiceImpl(VideoReviewDao videoReviewDao) {
      this.videoReviewDao = videoReviewDao;
   }

   // 영상에 대한 댓글 전체 조회
   @Override
   public List<VideoReviewView> getList(String videoId) {
      return videoReviewDao.selectAll(videoId);
   }

   // 영상에 대한 댓글 등록
   @Override
   public boolean writeReview(VideoReview review) {
      int result = videoReviewDao.insertReview(review);
      return result == 1;
   }

   // 영상에 대한 댓글 수정
   @Override
   public boolean modifyReview(VideoReview review) {
      int result = videoReviewDao.updateReview(review);
      return result == 1;
   }

   // 영상에 대한 댓글 삭제
   @Override
   public boolean removeReview(int reviewId) {
      int result = videoReviewDao.deleteReview(reviewId);
      return result == 1;
   }
   
}
