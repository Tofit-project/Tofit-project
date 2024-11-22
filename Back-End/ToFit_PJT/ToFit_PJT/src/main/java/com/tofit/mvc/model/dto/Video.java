package com.tofit.mvc.model.dto;

public class Video {
	private String videoId;
	private String channelName;
	private String title;
	private String thumbnail;
	private String url;
	private int cateCode;
	private int viewCnt;
	
	public Video(String videoId, String channelName, String title, String thumbnail, String url, int cateCode,
			int viewCnt) {
		this.videoId = videoId;
		this.channelName = channelName;
		this.title = title;
		this.thumbnail = thumbnail;
		this.url = url;
		this.cateCode = cateCode;
		this.viewCnt = viewCnt;
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

	public int getCateCode() {
		return cateCode;
	}

	public void setCateCode(int cateCode) {
		this.cateCode = cateCode;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", channelName=" + channelName + ", title=" + title + ", thumbnail="
				+ thumbnail + ", url=" + url + ", cateCode=" + cateCode + ", viewCnt=" + viewCnt + "]";
	}
	
	
}
