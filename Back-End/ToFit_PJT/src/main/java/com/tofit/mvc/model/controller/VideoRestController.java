package com.tofit.mvc.model.controller;

import java.util.List;

//import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.jwt.JwtUtil;
import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;
import com.tofit.mvc.model.dto.Video;
import com.tofit.mvc.model.service.VideoService;

@RestController
@RequestMapping("/tofit/video")
@CrossOrigin("*")
public class VideoRestController {
   
   private final VideoService videoService;
   private final JwtUtil jwtUtil;
//   private final OpenAiChatModel openAiChatModel;

   public VideoRestController(VideoService videoService, JwtUtil jwtUtil) {
      this.videoService = videoService;
      this.jwtUtil = jwtUtil;
//      this.openAiChatModel = openAiChatModel;
   }

   // 검색어 기반 영상 검색 (채널명, 제목)
   @GetMapping()
   public ResponseEntity<?> list(@ModelAttribute SearchCondition condition){
      List<SpecialistInfoView> list = videoService.search(condition);
      
      if(list == null || list.size() == 0)
         return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
      
      return new ResponseEntity<List<SpecialistInfoView>>(list, HttpStatus.OK);
   }
   
   
   
   // 사용자와 같은 나이대, 성별이 찜한 영상 추천
   @GetMapping("/recom")
   public ResponseEntity<?> recomList(@RequestHeader(value = "Authorization") String token){
      token = token.replace("Bearer ", "");
      String userId = jwtUtil.getUserIdFromToken(token);
      
      List<Video> list = videoService.getRecomVideo(userId);
      
      // 추천할 영상 정보가 없다면
      if(list == null || list.size() == 0) {
         // ******** 수정 필요 : gpt가 임의로 추천? or 조회수 내림차순 추천
//         String message = "너는 10년차 운동전문가야. 집에서 운동하는 사람들을 위해 운동영상을 5개 추천해줘."
//               + "#제약사항 1. videoId만 출력하기 2. videoId, videoId, videoId 와 같은 형식으로 출력하기"
//               + "영상 정보는 다음과 같아.\n";
//         List<Video> allVideo = videoService.getList();
//         for(int i=0; i<allVideo.size(); i++) {
//            message += allVideo.get(i).toString() + "\n";
//         }
//         String response = openAiChatModel.call(message);
         return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);         
      }
      
      return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
   }
   
   // 영상 상세 조회
   @GetMapping("/{videoId}")
   public ResponseEntity<?> detail(@PathVariable("videoId") String videoId){
      Video video = videoService.getVideo(videoId);
      
      if(video == null)
         return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
      
      return new ResponseEntity<Video>(video, HttpStatus.OK);
   }
   
}
