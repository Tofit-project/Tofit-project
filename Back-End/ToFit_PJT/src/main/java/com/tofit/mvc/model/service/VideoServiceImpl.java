package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.VideoDao;
import com.tofit.mvc.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {

	private final VideoDao videoDao;
	
	public VideoServiceImpl(VideoDao videoDao) {
		super();
		this.videoDao = videoDao;
	}

	// 영상 전체 조회
	@Override
	public List<Video> getList() {
		return videoDao.selectAll();
	}

//	// 추천 영상
//	@Override
//	public List<Video> getRecomVideo(String id) {
//		
//		
//		return null;
//	}

}