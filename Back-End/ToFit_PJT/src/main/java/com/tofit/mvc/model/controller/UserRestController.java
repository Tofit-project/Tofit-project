package com.tofit.mvc.model.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	public UserRestController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody User user) {
		try {
			// 비밀번호 해싱 처리 해야함!!!
			if (userService.registerUser(user)) {
				return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user");
		} catch (Exception e) {
			return exceptionHandling(e, "Failed to add user due to server error");
		}
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

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<>();
		User loginUser = userService.login(user.getId(), user.getPassword());

		System.out.println(loginUser);
		if (loginUser != null) {
			result.put("message", "login 성공");
			// userId를 토큰 생성시 포함!!
//			result.put("access-token", jwtUtil.createToken(loginUser.getName()));
			// id도 같이 넘겨주면 번거로운 작업을 할 필요는 없어
			status = HttpStatus.ACCEPTED;
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(result, status);
	}

	@PostMapping("/find-id")
	public ResponseEntity<String> fintId(@RequestBody User user) {
		String userId = userService.findUserId(user);
		if (userId != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userId);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found");
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
//			String userId = jwtUtil.getUserIdFormToken(token);
			String userId = "tester";

			User user = userService.getUserInfo(userId);
			if (user != null) {
				return ResponseEntity.status(HttpStatus.OK).body(user);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Info not found");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid");
		}
	}
	
	
	@PutMapping()
	public ResponseEntity<?> udtUserInfo(@RequestHeader("Authorization") String authHeader, @RequestBody User user) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

		if (token != null) {
//			String userId = jwtUtil.getUserIdFormToken(token);
			String userId = "tester";
			user.setUserId(userId); 

			if (userService.updateUserInfo(user)) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid");
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<?> removeUserAccount(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

		if (token != null) {
//			String userId = jwtUtil.getUserIdFormToken(token);
			String userId = "tester";

			if (userService.deleteUserAccount(userId)) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid");
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e, String customMessage) {
		e.printStackTrace();
		return new ResponseEntity<String>(customMessage + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
