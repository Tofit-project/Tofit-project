package com.tofit.mvc.model.dto;

import java.util.Date;

public class FeedReview {
	private int reviewId;
	private String userId;
	private int feedId;
	private String content;
	private Date regDate;
	
	public FeedReview(int reviewId, String userId, int feedId, String content, Date regDate) {
		super();
		this.reviewId = reviewId;
		this.userId = userId;
		this.feedId = feedId;
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
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
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
		return "FeedReview [reviewId=" + reviewId + ", userId=" + userId + ", feedId=" + feedId + ", content=" + content
				+ "]";
	}
	
	
	
}
