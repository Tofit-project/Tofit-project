package com.tofit.mvc.model.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.jwt.JwtUtil;
import com.tofit.mvc.model.service.FeedService;

@RestController
@RequestMapping("tofit/feeds")
@CrossOrigin("*")
public class FeedRestController {

	private final FeedService feedService;
	private final JwtUtil jwtUtil;
	
	public FeedRestController(FeedService feedService, JwtUtil jwtUtil) {
		super();
		this.feedService = feedService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping
	public ResponseEntity<String> follow(@RequestHeader("Authorization") String authHeader) {
	    String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
	    String userId = null;

	    if (token != null) {
	        userId = jwtUtil.getUserIdFromToken(token);
	    }
	    
	    return null;

	}
	
	
}
