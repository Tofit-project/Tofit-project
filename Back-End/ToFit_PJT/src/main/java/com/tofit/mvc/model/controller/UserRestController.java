package com.tofit.mvc.model.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.jwt.JwtUtil;
import com.tofit.mvc.model.dto.User;
import com.tofit.mvc.model.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserRestController {

	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder bcpe;

	public UserRestController(UserService userService, JwtUtil jwtUtil, BCryptPasswordEncoder bcpe) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.bcpe = bcpe;
	}

	
	@GetMapping("/id-check")
	public ResponseEntity<Boolean> checkId(@RequestParam String userId) {
		if (userService.checkUserId(userId)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(true);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
	}

	
	@GetMapping("/name-check")
	public ResponseEntity<Boolean> checkProfileName(@RequestParam String profileName) {
		if (userService.checkUserProfileName(profileName)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(true);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
	}

	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody User user) {
		try {
			// 비밀번호 해싱 처리
			user.setPassword(bcpe.encode(user.getPassword()));

			if (userService.registerUser(user)) {
				return ResponseEntity.status(HttpStatus.CREATED).body("사용자 추가 성공");
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용차 추가 실패");
		} catch (Exception e) {
			return exceptionHandling(e, "Failed to add user due to server error");
		}
	}

	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<>();

		try {
			// 해싱된 비밀번호를 비교할 User 객체를 가져옴
			User loginUser = userService.loginUser(user);

			if (loginUser != null && bcpe.matches(user.getPassword(), loginUser.getPassword())) {
				String token = jwtUtil.createToken(loginUser.getUserId(), loginUser.getProfileName());
				result.put("message", "login 성공");
				result.put("access-token", token);
				status = HttpStatus.ACCEPTED;
			} else {
				result.put("message", "login 실패 -> 잘못된 입력");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			return exceptionHandling(e, "Failed to login due to server error");
		}
		return new ResponseEntity<>(result, status);
	}

	
	@PostMapping("/find-id")
	public ResponseEntity<String> fintId(@RequestBody User user) {
		String userId = userService.findUserId(user);
		if (userId != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userId);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 ID 찾기 실패");
		}
	}

	
	@PutMapping("/reset-password")
	public ResponseEntity<Boolean> resetPw(@RequestBody User user) {
		// 비밀번호 재설정시에도 재설정하는 비밀번호 해싱처리 필요함!!!
		if (userService.resetUserPassword(user)) {
			return ResponseEntity.status(HttpStatus.OK).body(true);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	
	@GetMapping()
	public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

		if (token != null) {
			String userId = jwtUtil.getUserIdFromToken(token);

			
			if (userId != null) {
				User user = userService.getUserInfo(userId);
				if(user != null) {
					return ResponseEntity.status(HttpStatus.OK).body(user);
				}else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보 찾을 수 없음");
				}
				
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 JWT");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT 없음");
		}
	}

	
	@PutMapping()
	public ResponseEntity<?> udtUserInfo(@RequestHeader("Authorization") String authHeader, @RequestBody User user) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

		if (token != null) {
			String userId = jwtUtil.getUserIdFromToken(token);
			user.setUserId(userId);

			if (userService.updateUserInfo(user)) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT 없음");
		}
	}

	
	@DeleteMapping()
	public ResponseEntity<?> removeUserAccount(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

		if (token != null) {
			String userId = jwtUtil.getUserIdFromToken(token);

			if (userService.deleteUserAccount(userId)) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT 없음");
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e, String customMessage) {
		e.printStackTrace();
		return new ResponseEntity<String>(customMessage + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
