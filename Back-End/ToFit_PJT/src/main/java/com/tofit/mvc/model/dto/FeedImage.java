package com.tofit.mvc.model.dto;

public class FeedImage {
	private int imageId;
	private int feedId;
	private String img;
	
	public FeedImage() {
	}
	
	public FeedImage(int imageId, int feedId, String img) {
		super();
		this.imageId = imageId;
		this.feedId = feedId;
		this.img = img;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "FeedImage [imageId=" + imageId + ", feedId=" + feedId + ", img=" + img + "]";
	}
	
	
}
