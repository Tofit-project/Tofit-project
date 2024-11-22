package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.VideoDao;
import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;
import com.tofit.mvc.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {

	private final VideoDao videoDao;
	
	public VideoServiceImpl(VideoDao videoDao) {
		super();
		this.videoDao = videoDao;
	}

	// 추천 영상 조회
	@Override
	public List<Video> getRecomVideo(String userId) {
		return videoDao.selectRecomVideo(userId);
	}

	// 영상 조건 검색
	@Override
	public List<SpecialistInfoView> search(SearchCondition condition) {
		return videoDao.selectByCondition(condition);
	}

	// 영상 상세 조회
	@Override
	public Video getVideo(String videoId) {
		videoDao.updateViewCnt(videoId); // 조회수 증가
		return videoDao.selectVideo(videoId);
	}

}
