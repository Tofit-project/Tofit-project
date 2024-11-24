package com.tofit.mvc.model.dao;

import java.util.List;
import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;
import com.tofit.mvc.model.dto.Video;

public interface VideoDao {

   // 추천 영상 가져오기
   List<Video> selectRecomVideo(String userId);

   // 영상 조건 검색하기
   List<SpecialistInfoView> selectByCondition(SearchCondition condition);

   // 영상 상세 조회
   Video selectVideo(String videoId);

   // 영상 조회수 증가
   void updateViewCnt(String videoId);

}
