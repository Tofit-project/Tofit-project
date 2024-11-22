package com.tofit.mvc.model.dao;

import java.util.List;

import com.tofit.mvc.model.dto.FeedImage;

public interface FeedImageDao {

	// 피드 이미지 등록
	public int insertFeedImages(List<FeedImage> feedImages);

	// 전체 피드의 이미지 조회
	public List<FeedImage> selectAllFeedImages();

	// 피드 이미지 조회
	public List<FeedImage> selectFeedImagesByFeedId(int feedId);

	// 피드 이미지 삭제
	public int deleteFeedImages(int feedId);

}
