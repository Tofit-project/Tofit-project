package com.tofit.mvc.model.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tofit.mvc.jwt.JwtUtil;
import com.tofit.mvc.model.dto.Feed;
import com.tofit.mvc.model.service.FeedService;

@RestController
@RequestMapping("tofit/feeds")
@CrossOrigin("*")
public class FeedRestController {

	private final FeedService feedService;
	private final JwtUtil jwtUtil;
	
	public FeedRestController(FeedService feedService, JwtUtil jwtUtil) {
		this.feedService = feedService;
		this.jwtUtil = jwtUtil;
	}
	
	// 피드 작성
	@PostMapping("/write")
	public ResponseEntity<?> writeFeed(
	        @RequestHeader("Authorization") String authHeader,
	        @RequestParam String content,
	        @RequestPart(value = "files", required = false) MultipartFile[] files) {

	    String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

	    if (token != null) {
	        String userId = jwtUtil.getUserIdFromToken(token);

	        Feed obj = new Feed();
	        obj.setUserId(userId);
	        obj.setContent(content);

	        List<MultipartFile> fileList = files != null ? Arrays.asList(files) : new ArrayList<>();

	        if (feedService.registerFeed(obj, fileList)) {
	            return ResponseEntity.status(HttpStatus.CREATED).body(true);
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT 없음");
	}
	
	// 특정 사용자의 모든 피드와 피드별 이미지들 조회
	@GetMapping("/user")
	public ResponseEntity<?> getMyFeedList(@RequestHeader("Authorization") String authHeader){
		 String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
		 List<Map<String, Object>> res = new ArrayList<>();
		 
		 if(token != null) {
			 String userId = jwtUtil.getUserIdFromToken(token);
			 
			 if(userId != null) {
				 res = feedService.getFeedListByUserId(userId);
				 System.out.println(res);
				 return ResponseEntity.status(HttpStatus.OK).body(res);
			 }else{
				 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피드 정보 없음");
			 }
		 } else {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT 없음");
		 }
		 
	}
	

	
	
}
