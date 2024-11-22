package com.tofit.mvc.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.model.dao.FeedDao;
import com.tofit.mvc.model.dao.FeedImageDao;
import com.tofit.mvc.model.dto.Feed;
import com.tofit.mvc.model.dto.FeedImage;

@RestController
public class FeedServiceImpl implements FeedService{
	
	private final FeedDao feedDao;
	private final FeedImageDao feedImageDao;

	public FeedServiceImpl(FeedDao feedDao, FeedImageDao feedImageDao) {
		this.feedDao = feedDao;
		this.feedImageDao = feedImageDao;
	}
	
	// 피드 작성
	@Transactional
	@Override
	public boolean registerFeed(Feed feed, List<FeedImage> feedImages) {
		boolean insertSuccess = feedDao.insertFeedOne(feed) == 1;

        if (insertSuccess) {
        	
        	// 피드 이미지 있을 경우 등록
        	if (feedImages != null && !feedImages.isEmpty()) {
                for (FeedImage obj : feedImages) {
                    obj.setFeedId(feed.getFeedId());
                }
                return feedImageDao.insertFeedImages(feedImages) == feedImages.size();
            }
        	return true;
        }
        return false;
	}
	
	// 전체 피드와 피드별 이미지들 조회
	@Override
	public List<Map<String, Object>> getFeedList() {
		
		List<Feed> feedList = feedDao.selectFeedList();
		List<FeedImage> feedImageList = feedImageDao.selectAllFeedImages();
		
		List<Map<String, Object>> res = new ArrayList<>();
		
		// Feed에 해당하는 FeedImage 리스트를 매핑
		for(Feed feed : feedList) {
			List<FeedImage> imagesFromFeed = new ArrayList<>();
			
			// FeedImage 리스트에서 해당 Feed의 id에 맞는 이미지들 필터링
			for(FeedImage image : feedImageList) {
				if(image.getFeedId() == feed.getFeedId()) {
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
		
		for(Feed feed : feedList) {
			
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
		if(!exist.getUserId().equals(userId)) {
			return false;
		}
		
		exist.setContent(feed.getContent());
		boolean updateSuccess = feedDao.updateFeedOne(feed) == 1;
		
		if(updateSuccess) {
			
			// 기존 피드 이미지 삭제
			feedImageDao.deleteFeedImages(feedId);
			
			if(feedImages != null && feedImages.size() > 0) {
				for(FeedImage image : feedImages) {
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
