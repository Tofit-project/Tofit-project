package com.tofit.mvc.model.dto;

public class SpecialistInfoView {
	private String videoId;
	private String channelName;
	private String title;
	private String thumbnail;
	private String url;
	private String goal;
	private int viewCnt;
	private String hashtag;
	private String profileImg;
	
	public SpecialistInfoView(String videoId, String channelName, String title, String thumbnail, String url,
			String goal, int viewCnt, String hashtag, String profileImg) {
		this.videoId = videoId;
		this.channelName = channelName;
		this.title = title;
		this.thumbnail = thumbnail;
		this.url = url;
		this.goal = goal;
		this.viewCnt = viewCnt;
		this.hashtag = hashtag;
		this.profileImg = profileImg;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
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
		return "SpecialistInfoView [videoId=" + videoId + ", channelName=" + channelName + ", title=" + title
				+ ", thumbnail=" + thumbnail + ", url=" + url + ", goal=" + goal + ", viewCnt=" + viewCnt + ", hashtag="
				+ hashtag + ", profileImg=" + profileImg + "]";
	}
	
	
}
