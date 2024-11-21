package com.tofit.mvc.model.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;
import com.tofit.mvc.model.dto.Video;
import com.tofit.mvc.model.service.VideoService;

@RestController
@RequestMapping("/tofit/video")
public class VideoRestController {
	
	private final VideoService videoService;

	public VideoRestController(VideoService videoService) {
		this.videoService = videoService;
	}
	
//	// 비디오 전체 조회
//	@GetMapping()
//	public ResponseEntity<?> list(){
//		List<Video> list = videoService.getList();
//		
//		if(list == null || list.size() == 0)
//			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//		
//		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
//	}
	// 비디오 조건 검색
	@GetMapping()
	public ResponseEntity<?> list(@ModelAttribute SearchCondition condition){
		List<SpecialistInfoView> list = videoService.search(condition);
		
		if(list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<SpecialistInfoView>>(list, HttpStatus.OK);
	}
	
	// 비디오 추천
	@GetMapping("/recom")
	public ResponseEntity<?> recomList(){
		
		String userId = "user2";
		List<Video> list = videoService.getRecomVideo(userId);
		
		// 추천할 영상 정보가 없다면
		if(list == null || list.size() == 0) {
			// ******** 수정 필요 : gpt가 임의로 추천? or 조회수 내림차순 추천
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);			
		}
		
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}
	
	
}
