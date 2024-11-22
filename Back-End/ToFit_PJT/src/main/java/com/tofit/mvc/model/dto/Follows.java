package com.tofit.mvc.model.dto;

import java.util.Date;

public class Follows {
	private int followsId;
	private String followerId;
	private String followedId;
	private int checked;
	private Date regDate;

	public Follows() {
	}

	public Follows(int followsId, String followerId, String followedId, int checked, Date regDate) {
		this.followsId = followsId;
		this.followerId = followerId;
		this.followedId = followedId;
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

	public String getFollowedId() {
		return followedId;
	}

	public void setFollowedId(String followedId) {
		this.followedId = followedId;
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
		return "Follows [followsId=" + followsId + ", followerId=" + followerId + ", followedId=" + followedId
				+ ", checked=" + checked + ", regDate=" + regDate + "]";
	}

}
