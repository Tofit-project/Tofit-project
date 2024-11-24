package com.tofit.mvc.model.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.jwt.JwtUtil;
import com.tofit.mvc.model.dto.VideoReview;
import com.tofit.mvc.model.dto.VideoReviewView;
import com.tofit.mvc.model.service.VideoReviewService;

@RestController
@RequestMapping("/tofit/video/{videoId}/review")
@CrossOrigin("*")
public class VideoReviewRestController {

   private final VideoReviewService videoReviewService;
   private final JwtUtil jwtUtil;
   
   public VideoReviewRestController(VideoReviewService videoReviewService, JwtUtil jwtUtil) {
      this.videoReviewService = videoReviewService;
      this.jwtUtil = jwtUtil;
   }

   // 전체 댓글 조회
   @GetMapping()
   public ResponseEntity<?> list(@PathVariable("videoId") String videoId) {
      // 댓글 목록
      List<VideoReviewView> list = videoReviewService.getList(videoId);

      // 댓글 목록에 있는 유저 정보
      
      if (list == null || list.size() == 0) {
         return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<List<VideoReviewView>>(list, HttpStatus.OK);
   }

   // 댓글 등록
   @PostMapping()
   public ResponseEntity<?> write(@PathVariable("videoId") String videoId,
         @RequestHeader(value = "Authorization") String token, @RequestBody VideoReview review) {
      System.out.println(review);
      
      token = token.replace("Bearer ", "");
      String userId = jwtUtil.getUserIdFromToken(token);

      review.setVideoId(videoId);
      review.setUserId(userId);
      
      System.out.println(review);

      boolean isOk = videoReviewService.writeReview(review);
      if (isOk)
         return new ResponseEntity<String>("등록 완료", HttpStatus.CREATED);

      return new ResponseEntity<String>("등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
   }

   // 댓글 수정
   @PutMapping("/{reviewId}")
   public ResponseEntity<?> update(@PathVariable("reviewId") int reviewId, @RequestBody VideoReview review) {
      review.setReviewId(reviewId);

      boolean isOk = videoReviewService.modifyReview(review);

      if (isOk)
         return new ResponseEntity<String>(review.getContent(), HttpStatus.ACCEPTED);
      return new ResponseEntity<String>("수정 불가", HttpStatus.SERVICE_UNAVAILABLE);
   }

   // 댓글 삭제
   @DeleteMapping("/{reviewId}")
   public ResponseEntity<?> remove(@PathVariable("reviewId") int reviewId) {
      boolean isOk = videoReviewService.removeReview(reviewId);

      if (isOk)
         return new ResponseEntity<String>("삭제 완료", HttpStatus.ACCEPTED);
      return new ResponseEntity<String>("삭제 불가", HttpStatus.SERVICE_UNAVAILABLE);
   }
}
