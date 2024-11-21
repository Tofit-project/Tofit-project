package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;
import com.tofit.mvc.model.dto.Video;

public interface VideoService {

	List<Video> getList();

	List<Video> getRecomVideo(String userId);

	List<SpecialistInfoView> search(SearchCondition condition);


}
