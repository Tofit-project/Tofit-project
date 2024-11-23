package com.tofit.mvc.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.tofit.mvc.model.dto.User;

public interface UserService {

	// 회원가입
	public boolean registerUser(User user,  MultipartFile file);

	// 아이디 중복 확인
	public boolean checkUserId(String id);

	// 프로필명 중복 확인
	public boolean checkUserProfileName(String name);

	// 로그인
	public User loginUser(User user);

	// 아이디 찾기
	public String findUserId(User user);

	// 비밀번호 재설정
	public boolean resetUserPassword(User user);

	// 회원 정보 조회
	public User getUserInfo(String id);

	// 회원 정보 수정
	public boolean updateUserInfo(User user);

	// 회원 탈퇴
	public boolean deleteUserAccount(String id);

}
