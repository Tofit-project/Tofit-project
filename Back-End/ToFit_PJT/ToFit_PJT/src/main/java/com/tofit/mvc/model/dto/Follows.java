package com.tofit.mvc.model.dto;

import java.util.Date;

public class Follows {
	private int followsId;
	private String followerId;
	private String follewedId;
	private int checked;
	private Date regDate;
	
	public Follows(int followsId, String followerId, String follewedId, int checked, Date regDate) {
		super();
		this.followsId = followsId;
		this.followerId = followerId;
		this.follewedId = follewedId;
		this.checked = checked;
		this.regDate = regDate;
	}
	public int getFollowsId() {
		return followsId;
	}
	public void setFollowsId(int followsId) {
		this.followsId = followsId;
	}
	public String getFollowerId() {
		return followerId;
	}
	public void setFollowerId(String followerId) {
		this.followerId = followerId;
	}
	public String getFollewedId() {
		return follewedId;
	}
	public void setFollewedId(String follewedId) {
		this.follewedId = follewedId;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "Follows [followsId=" + followsId + ", followerId=" + followerId + ", follewedId=" + follewedId
				+ ", checked=" + checked + ", regDate=" + regDate + "]";
	}
	
	
}