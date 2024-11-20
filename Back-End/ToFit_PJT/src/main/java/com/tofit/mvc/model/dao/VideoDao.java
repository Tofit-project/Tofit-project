package com.tofit.mvc.model.dao;

import java.util.List;

import com.tofit.mvc.model.dto.Video;

public interface VideoDao {

	// 영상 전체 가져오기
	List<Video> selectAll();

}
