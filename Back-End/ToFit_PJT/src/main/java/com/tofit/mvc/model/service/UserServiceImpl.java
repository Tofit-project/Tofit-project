package com.tofit.mvc.model.service;

import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.model.dao.UserDao;
import com.tofit.mvc.model.dto.User;

@RestController
public class UserServiceImpl implements UserService{
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean registerUser(User user) {
		return userDao.insertUserOne(user) == 1;
	}

	@Override
	public boolean checkUserId(String id) {
		return userDao.selectUserIdCheck(id) == 1;
	}

	@Override
	public boolean checkUserProfileName(String name) {
		return userDao.selectUserProfileNameCheck(name) == 1;
	}

	@Override
	public User loginUser(User user) {
		return userDao.selectUserLogin(user);
	}

	@Override
	public String findUserId(User user) {
		return userDao.selectUserIdFind(user);
	}

	@Override
	public boolean resetUserPassword(User user) {
		return userDao.updateUserPassword(user) == 1;
	}

	@Override
	public User getUserInfo(String id) {
		return userDao.selectUserOne(id);
	}

	@Override
	public boolean updateUserInfo(User user) {
		return userDao.updateUserOne(user) == 1;
	}

	@Override
	public boolean deleteUserAccount(String id) {
		return userDao.deleteUserOne(id) == 1;
	}

	

}
