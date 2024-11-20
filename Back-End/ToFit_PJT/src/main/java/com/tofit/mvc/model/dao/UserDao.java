package com.tofit.mvc.model.dao;

import com.tofit.mvc.model.dto.User;

public interface UserDao {
	
	// 회원가입
	public int insertUserOne(User user);
	
	// 아이디 중복 확인
	public int selectUserIdCheck(String id);
	
	// 프로필명 중복 확인
	public int selectUserNameCheck(String name);
	
	// 로그인
	public User selectUserLogin(User user);
	
	// 아이디 찾기
	public String selectUserIdFind(User user);
	
	// 비밀번호 재설정
	public int updateUserPassword(User user);
	
	// 회원 정보 조회
	public User selectUserOne(String id);
	
	// 회원 정보 수정
	public int updateUserOne(User user);
	
	// 회원 탈퇴
	public int deleteUserOne(String id);

}
