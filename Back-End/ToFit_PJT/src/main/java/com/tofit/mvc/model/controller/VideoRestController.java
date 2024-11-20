package com.tofit.mvc.model.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.model.dto.Video;
import com.tofit.mvc.model.service.VideoService;

@RestController
@RequestMapping("/tofit")
public class VideoRestController {
	
	private final VideoService videoService;

	public VideoRestController(VideoService videoService) {
		this.videoService = videoService;
	}
	
	// 비디오 전체 조회
	@GetMapping("/video")
	public ResponseEntity<?> list(){
		List<Video> list = videoService.getList();
		
		if(list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}
	
//	// 비디오 추천
//	@GetMapping("/video")
//	public ResponseEntity<?> list(){
//		
//		String id = "user1";
//		List<Video> list = videoService.getRecomVideo(id);
//		
//		if(list == null || list.size() == 0)
//			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//		
//		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
//	}
	
	// 비디오 조건 검색
	
	
}
