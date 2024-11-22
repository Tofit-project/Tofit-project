package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.FollowsDao;
import com.tofit.mvc.model.dto.Follows;

@Service
public class FollowsServiceImpl implements FollowsService{
	
	private final FollowsDao followsDao;

	public FollowsServiceImpl(FollowsDao followsDao) {
		this.followsDao = followsDao;
	}

	@Override
	public Boolean registerFollow(Follows follows) {
		return followsDao.insertFollow(follows) == 1;
	}

	@Override
	public Boolean removeFollow(Follows follows) {
		return followsDao.deleteFollow(follows) == 1;
	}

	@Override
	public Boolean changeFollowCheckStatus(Follows follows) {
		return followsDao.updateFollowCheckStatus(follows) == 1;
	}

	@Override
	public List<Follows> getFollowList(String id) {
		return followsDao.selsectFollowList(id);
	}

}
