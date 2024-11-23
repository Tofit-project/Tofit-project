package com.tofit.mvc.model.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tofit.mvc.model.dao.UserDao;
import com.tofit.mvc.model.dto.User;

@RestController
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final AmazonS3Client amazonS3Client;
	private final String bucket;

	public UserServiceImpl(UserDao userDao, AmazonS3Client amazonS3Client,
			@Value("${cloud.aws.s3.bucket}") String bucket) {
		this.userDao = userDao;
		this.amazonS3Client = amazonS3Client;
		this.bucket = bucket;
	}

//////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean registerUser(User user, MultipartFile file) {

		if (file != null && file.getSize() > 0) {
			try {
				String profileImgUrl = uploadFileToS3(file);
				user.setProfileImg(profileImgUrl);
			} catch (IOException e) {
				throw new RuntimeException("파일 업로드 실패", e);
			}
		} else {
			user.setProfileImg(null); 
		}
		return userDao.insertUserOne(user) == 1;
	}

	private String uploadFileToS3(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

		// 업로드되면 파일url 반환
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

////////////////////////////////////////////////////////////////////////////////////////

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
