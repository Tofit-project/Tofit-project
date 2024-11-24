package com.tofit.mvc.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tofit.mvc.model.dao.FeedDao;
import com.tofit.mvc.model.dao.FeedImageDao;
import com.tofit.mvc.model.dto.Feed;
import com.tofit.mvc.model.dto.FeedImage;

@RestController
public class FeedServiceImpl implements FeedService {

	private final FeedDao feedDao;
	private final FeedImageDao feedImageDao;
	private final AmazonS3Client amazonS3Client;
	private final String bucket;

	public FeedServiceImpl(FeedDao feedDao, FeedImageDao feedImageDao, AmazonS3Client amazonS3Client,
			@Value("${cloud.aws.s3.bucket}") String bucket) {
		this.feedDao = feedDao;
		this.feedImageDao = feedImageDao;
		this.amazonS3Client = amazonS3Client;
		this.bucket = bucket;
	}

/////////////// 피드 작성 /////////////////////////////////////////////////////////////////////////////	
	@Transactional
	@Override
	public boolean registerFeed(Feed feed, List<MultipartFile> files) {

		// 피드부터 저장
		boolean insertSuccess = feedDao.insertFeedOne(feed) == 1;
		if (!insertSuccess) {
			throw new RuntimeException("Feed 저장 실패");
		}

		// 사진 업로드 안했을 경우 처리
		if (files == null && files.size() <= 0) {
			return true;
		}

		List<FeedImage> feedImages = files.parallelStream().map(file -> {
			try {
				// s3에 업로드
				String imageUrl = uploadFileToS3(file);
				
				// 피드 이미지 데이터 담기
				FeedImage feedImage = new FeedImage();
				feedImage.setFeedId(feed.getFeedId()); 
				feedImage.setImg(imageUrl);
				return feedImage;
			} catch (IOException e) {
				throw new RuntimeException("파일 업로드 실패", e);
			}
		}).toList();
		
		
		// 피드 이미지 db에 저장
		if(!feedImages.isEmpty()) {
			int res = feedImageDao.insertFeedImages(feedImages);
			if(res != feedImages.size()) {
				 throw new RuntimeException("FeedImage 저장 실패");
			}
		}
		return true;
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

////////////////////////////////////////////////////////////////////////////////////////////////

	// 전체 피드와 피드별 이미지들 조회
	@Override
	public List<Map<String, Object>> getFeedList() {

		List<Feed> feedList = feedDao.selectFeedList();
		List<FeedImage> feedImageList = feedImageDao.selectAllFeedImages();

		List<Map<String, Object>> res = new ArrayList<>();

		// Feed에 해당하는 FeedImage 리스트를 매핑
		for (Feed feed : feedList) {
			List<FeedImage> imagesFromFeed = new ArrayList<>();

			// FeedImage 리스트에서 해당 Feed의 id에 맞는 이미지들 필터링
			for (FeedImage image : feedImageList) {
				if (image.getFeedId() == feed.getFeedId()) {
					imagesFromFeed.add(image);
				}
			}

			Map<String, Object> feedWithImages = new HashMap<>();
			feedWithImages.put("feed", feed);
			feedWithImages.put("images", imagesFromFeed);

			res.add(feedWithImages);
		}
		return res;
	}

	// 특정 사용자의 모든 피드와 피드별 이미지들 조회
	@Override
	public List<Map<String, Object>> getFeedListByUserId(String userId) {

		List<Feed> feedList = feedDao.selectFeedListByUserId(userId);

		List<Map<String, Object>> res = new ArrayList<>();

		for (Feed feed : feedList) {

			List<FeedImage> feedImageList = feedImageDao.selectFeedImagesByFeedId(feed.getFeedId());
			
			Map<String, Object> feedWithImages = new HashMap<>();
			feedWithImages.put("feed", feed);
			feedWithImages.put("images", feedImageList);

			res.add(feedWithImages);
		}
		return res;
	}

	// 피드 상세 조회
	@Override
	public Feed getFeedInfo(int feedId) {
		return feedDao.selectFeedOne(feedId);
	}

	// 피드 수정
	@Transactional
	@Override
	public boolean updateFeedInfo(int feedId, String userId, Feed feed, List<FeedImage> feedImages) {

		// 피드 등록한 userId와 로그인된 userId가 동일한지 확인
		Feed exist = feedDao.selectFeedOne(feedId);
		if (!exist.getUserId().equals(userId)) {
			return false;
		}

		exist.setContent(feed.getContent());
		boolean updateSuccess = feedDao.updateFeedOne(feed) == 1;

		if (updateSuccess) {

			// 기존 피드 이미지 삭제
			feedImageDao.deleteFeedImages(feedId);

			if (feedImages != null && feedImages.size() > 0) {
				for (FeedImage image : feedImages) {
					image.setFeedId(feedId);
				}
				return feedImageDao.insertFeedImages(feedImages) == feedImages.size();
			}
			return true; // 이미지 변경 없더라도 피드 내용은 수정 완료
		}
		return false;

	}

	// 피드 삭제
	@Override
	public boolean deleteFeedInfo(int feedId, String userId) {
		Feed exist = feedDao.selectFeedOne(feedId);
		if (exist.getUserId().equals(userId)) {
			return feedDao.deleteFeedOne(feedId) > 0;
		}
		return false;
	}

}
