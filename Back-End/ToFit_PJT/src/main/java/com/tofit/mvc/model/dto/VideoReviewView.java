package com.tofit.mvc.model.dto;

import java.util.Date;

public class VideoReviewView {
   private int reviewId;
   private String userId;
   private String videoId;
   private String content;
   private String profileName;
   private String profileImg;
   private Date regDate;
   
   public VideoReviewView(int reviewId, String userId, String videoId, String content, String profileName,
         String profileImg, Date regDate) {
      super();
      this.reviewId = reviewId;
      this.userId = userId;
      this.videoId = videoId;
      this.content = content;
      this.profileName = profileName;
      this.profileImg = profileImg;
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

   public String getProfileName() {
      return profileName;
   }

   public void setProfileName(String profileName) {
      this.profileName = profileName;
   }

   public String getProfileImg() {
      return profileImg;
   }

   public void setProfileImg(String profileImg) {
      this.profileImg = profileImg;
   }

   public Date getRegDate() {
      return regDate;
   }

   public void setRegDate(Date regDate) {
      this.regDate = regDate;
   }

   @Override
   public String toString() {
      return "VideoReviewView [reviewId=" + reviewId + ", userId=" + userId + ", videoId=" + videoId + ", content="
            + content + ", profileName=" + profileName + ", profileImg=" + profileImg + ", regDate=" + regDate
            + "]";
   }
   
}
