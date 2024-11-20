package com.tofit.mvc.model.dto;

public class Specialist {
	private String specialistId;
	private int cateCode;
	private String hashtag;
	private String profileImg;
	
	public Specialist(String specialistId, int cateCode, String hashtag, String profileImg) {
		super();
		this.specialistId = specialistId;
		this.cateCode = cateCode;
		this.hashtag = hashtag;
		this.profileImg = profileImg;
	}

	public String getSpecialistId() {
		return specialistId;
	}

	public void setSpecialistId(String specialistId) {
		this.specialistId = specialistId;
	}

	public int getCateCode() {
		return cateCode;
	}

	public void setCateCode(int cateCode) {
		this.cateCode = cateCode;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	@Override
	public String toString() {
		return "Specialist [specialistId=" + specialistId + ", cateCode=" + cateCode + ", hashtag=" + hashtag
				+ ", profileImg=" + profileImg + "]";
	}
	
	
	
}
