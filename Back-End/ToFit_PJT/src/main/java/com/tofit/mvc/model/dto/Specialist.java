package com.tofit.mvc.model.dto;

public class Specialist {
    private String specialistId;
    private String goal;
    private String hashtag;
    private String profileImg;
    
    public Specialist(String specialistId, String goal, String hashtag, String profileImg) {
        this.specialistId = specialistId;
        this.goal = goal;
        this.hashtag = hashtag;
        this.profileImg = profileImg;
    }

    public String getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(String specialistId) {
        this.specialistId = specialistId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
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
        return "Specialist [specialistId=" + specialistId + ", goal=" + goal + ", hashtag=" + hashtag + ", profileImg="
                + profileImg + "]";
    }
    
    
    
}