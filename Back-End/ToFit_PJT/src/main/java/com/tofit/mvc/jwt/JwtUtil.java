package com.tofit.mvc.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	// 보안 키 설정
	private String key = "SSAFY_TofitProject_Config_SecretKey";
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	
	// 토큰 유효기간
    private static final long TOKEN_VALID_TIME = 1000 * 60 * 60 * 4;
	
	// 토큰 생성
	public String createToken(String userId, String profileName) {
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userId);
		claims.put("profileName", profileName);
		
		return Jwts.builder()
				.claims(claims) // 사용자 정보
				.issuedAt(new Date(System.currentTimeMillis())) // 생성시간
				.expiration(new Date(System.currentTimeMillis() + TOKEN_VALID_TIME)) // 만료시간
				.signWith(secretKey).compact();
	}
	
	
	// 토큰 검증
	private Jws<Claims> validateJwt(String token){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
		
	}
	
	// 토근 검증한 후 userId 추출
	public String getUserIdFromToken(String token) {
		Jws<Claims> jws = validateJwt(token);
		Claims claims = jws.getPayload();
		return claims.get("userId", String.class);
	}
}
