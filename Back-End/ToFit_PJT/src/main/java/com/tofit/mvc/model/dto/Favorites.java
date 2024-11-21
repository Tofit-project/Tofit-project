package com.tofit.mvc.model.dto;

import java.util.Date;

public class Favorites {
	private int favoritesId;
	private String userId;
	private String videoId;
	private Date createdDate;

	public Favorites() {
	}

	public Favorites(int favoritesId, String userId, String videoId, Date createdDate) {
		super();
		this.favoritesId = favoritesId;
		this.userId = userId;
		this.videoId = videoId;
		this.createdDate = createdDate;
	}

	public int getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(int favoritesId) {
		this.favoritesId = favoritesId;
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

	public Date getcreatedDate() {
		return createdDate;
	}

	public void setcreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Favorites [favoritesId=" + favoritesId + ", userId=" + userId + ", videoId=" + videoId + ", createdDate="
				+ createdDate + "]";
	}

}
