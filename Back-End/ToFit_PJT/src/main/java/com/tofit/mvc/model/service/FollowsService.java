package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.Follows;

public interface FollowsService {

	// 팔로우 등록
	public Boolean registerFollow(Follows follows);

	// 팔로우 삭제
	public Boolean removeFollow(Follows follows);

	// 팔로우 알림 상태 변경(followed가 확인한 상태로 변경)
	public Boolean changeFollowCheckStatus(Follows follows);
	
	// 사용자 팔로우 전체 조회 
	public List<Follows> getFollowList(String id);

}
