package com.tofit.mvc.model.dto;

import java.util.Date;

public class VideoReview {
	private int reviewId;
	private String userId;
	private String videoId;
	private String content;
	private Date regDate;
	
	public VideoReview() {
	}
	
	public VideoReview(int reviewId, String userId, String videoId, String content, Date regDate) {
		super();
		this.reviewId = reviewId;
		this.userId = userId;
		this.videoId = videoId;
		this.content = content;
		this.regDate = regDate;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "VideoReview [reviewId=" + reviewId + ", userId=" + userId + ", videoId=" + videoId + ", content="
				+ content + ", regDate=" + regDate + "]";
	}
	
	
}
