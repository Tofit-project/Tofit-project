package com.tofit.mvc.model.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.jwt.JwtUtil;
import com.tofit.mvc.model.dto.Follows;
import com.tofit.mvc.model.service.FollowsService;

@RestController
@RequestMapping("tofit/follows")
@CrossOrigin("*")
public class FollowsRestController {

	private final FollowsService followsService;
	private final JwtUtil jwtUtil;

	public FollowsRestController(FollowsService followsService, JwtUtil jwtUtil) {
		this.followsService = followsService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping
	public ResponseEntity<String> follow(@RequestParam("followedId") String followedId,
	        @RequestHeader("Authorization") String authHeader) {
	    String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
	    String userId = null;

	    if (token != null) {
	        userId = jwtUtil.getUserIdFromToken(token);
	        if (userId != null) {
	            Follows follows = new Follows();
	            follows.setFollowerId(userId);
	            follows.setFollowedId(followedId);

	            if (followsService.registerFollow(follows)) {
	                return ResponseEntity.status(HttpStatus.CREATED).body("팔로우 등록 성공");
	            } else {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("팔로우 등록 실패");
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 JWT");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("jwt 없음");
	    }
	}


	@DeleteMapping
	public ResponseEntity<String> followCancle(@RequestParam("followedId") String followedId,
			@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

		if (token == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("jwt 없음");
	    }
		
		String userId = jwtUtil.getUserIdFromToken(token);
	    if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 JWT");
	    }
		
	    Follows follows = new Follows();
	    follows.setFollowerId(userId);
	    follows.setFollowedId(followedId);
		
	    if (!followsService.removeFollow(follows)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("팔로우 삭제 실패");
	    }

		return ResponseEntity.status(HttpStatus.OK).body("팔로우 삭제 성공");
	}

	@PutMapping("/check-status")
	public ResponseEntity<String> followChecked(@RequestParam("followedId") String followedId,
			@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
		String userId = null;

		if (token != null) {
			userId = jwtUtil.getUserIdFromToken(token);
		}

		if (userId != null) {
			Follows follows = new Follows();
			follows.setFollowerId(userId);
			follows.setFollowedId(followedId);

			if (followsService.changeFollowCheckStatus(follows)) {
				return ResponseEntity.status(HttpStatus.OK).body("알림 상태 변경 성공");
			}
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알림 상태 변경 실패");
	}

	@GetMapping
	public ResponseEntity<List<Follows>> getFollowsByUserId(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
		String userId = null;

		if (token != null) {
			userId = jwtUtil.getUserIdFromToken(token);
		}

		if (userId != null) {
			List<Follows> followsList = followsService.getFollowList(userId);

			if (followsList != null && followsList.size() > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(followsList);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(followsList);
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
