package com.tofit.mvc.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String key = "SSAFY_NonMajor_JavaTrack_SecretKey";
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	
	//토큰 생성시 다양한 데이터를 저장할 수 있음 (DTO or Map)
	public String createToken(String userId) {
		//유효기간 
		Date exp = new Date(System.currentTimeMillis()+ 1000*60*60); //1시간
		return Jwts.builder().header().add("typ", "JWT").and()
				.claim("userId", userId).expiration(exp) // subject 이런건 자동으로 만들어지는게 있지만, 내가 기록하고 싶은건 claim에 작성
				.signWith(secretKey).compact();
	}
	
	
	// 유효성 검증 방법 생각해보기!!!
	//유효성 검증 (실제로 내용물을 확인하기 위함은 아니고요... 
	//이거 실행했을때 에러나면 유효기간 지난거....
	public Jws<Claims> vaildate(String token ){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
	
//	public Jws<Claims> validate(String token) {
//	    return Jwts.parserBuilder()  // parser() 대신 parserBuilder() 사용
//	            .setSigningKey(secretKey)  // 서명 키 설정
//	            .build()  // 파서 빌드
//	            .parseClaimsJws(token);  // JWT를 파싱하고 유효성 검증
//	}
	
//	public String getUserIdFromToken(String token) {
//	    try {
//	        // 토큰 검증 및 파싱
//	        Jws<Claims> claimsJws = vaildate(token); 
//	        
//	        // Claims 객체에서 userId 추출
//	        Claims claims = claimsJws.getBody(); 
//	        
//	        // userId 값을 반환
//	        return claims.get("userId", String.class); 
//	        
//	    } catch (Exception e) {
//	        // 토큰이 유효하지 않거나 만료된 경우 처리
//	        return null;
//	    }
//	}
	
	




	


	
	
	


	
	
	
	
	
	
	
	
	
	
	
}