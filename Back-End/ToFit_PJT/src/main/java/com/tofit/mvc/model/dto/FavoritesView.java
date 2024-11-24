package com.tofit.mvc.model.dto;

public class FavoritesView {
   private int favoritesId;
   private String userId;
   private String videoId;
   private String title;
   private String channelName;
   private String goal;
   private String createdDate;
   
   public FavoritesView(int favoritesId, String userId, String videoId, String title, String channelName, String goal,
         String createdDate) {
      this.favoritesId = favoritesId;
      this.userId = userId;
      this.videoId = videoId;
      this.title = title;
      this.channelName = channelName;
      this.goal = goal;
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

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getChannelName() {
      return channelName;
   }

   public void setChannelName(String channelName) {
      this.channelName = channelName;
   }

   public String getGoal() {
      return goal;
   }

   public void setGoal(String goal) {
      this.goal = goal;
   }

   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   @Override
   public String toString() {
      return "FavoritesView [favoritesId=" + favoritesId + ", userId=" + userId + ", videoId=" + videoId + ", title="
            + title + ", channelName=" + channelName + ", goal=" + goal + ", createdDate=" + createdDate + "]";
   }
   
}
