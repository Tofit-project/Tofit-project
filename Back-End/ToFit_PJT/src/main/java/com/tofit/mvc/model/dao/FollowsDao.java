package com.tofit.mvc.model.dao;

import java.util.List;

import com.tofit.mvc.model.dto.Follows;

public interface FollowsDao {
	
	// 팔로우 등록
	public int insertFollow(Follows follows);
	
	// 팔로우 삭제
	public int deleteFollow(Follows follows);
	
	// 팔로우 알림 상태 변경(followed가 확인한 상태로 변경)
	public int updateFollowCheckStatus(Follows follows);
	
	// 사용자 팔로우 전체 조회
	public List<Follows> selsectFollowList(String id);

}
