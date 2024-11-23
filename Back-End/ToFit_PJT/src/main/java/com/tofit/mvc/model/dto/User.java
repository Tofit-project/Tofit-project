package com.tofit.mvc.model.dto;

import java.util.Date;

public class User {
	private String userId;
	private String password;
	private String email;
	private String gender;
	private Date birth;
	private String profileName;
	private String profileImg;
	private int loginType;
	private Date regDate;
	
	public User() {
	}

	public User(String userId, String password, String email, String gender, Date birth, String profileName,
			String profileImg, int loginType, Date regDate) {
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.birth = birth;
		this.profileName = profileName;
		this.profileImg = profileImg;
		this.loginType = loginType;
		this.regDate = regDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", email=" + email + ", gender=" + gender
				+ ", birth=" + birth + ", profileName=" + profileName + ", profileImg=" + profileImg + ", loginType="
				+ loginType + ", regDate=" + regDate + "]";
	}
	
	
	
}
