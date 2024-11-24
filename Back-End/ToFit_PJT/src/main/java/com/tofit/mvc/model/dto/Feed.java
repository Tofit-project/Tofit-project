package com.tofit.mvc.model.dto;

import java.util.Date;

public class Feed {
	private int feedId;
	private String userId;
	private String content;
	private Date regDate;
	
	public Feed() {
	}
	
	public Feed(int feedId, String userId, String content, Date regDate) {
		super();
		this.feedId = feedId;
		this.userId = userId;
		this.content = content;
		this.regDate = regDate;
	}
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
		return "Feed [feedId=" + feedId + ", userId=" + userId + ", content=" + content + ", regDate=" + regDate + "]";
	}
	
	
	
}
