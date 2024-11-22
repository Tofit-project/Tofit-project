package com.tofit.mvc.model.dao;

import java.util.List;

import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.Specialist;
import com.tofit.mvc.model.dto.SpecialistInfoView;

public interface SpecialistDao {

	// 전문가 조건(이름) 검색
	List<Specialist> selectByCondition(SearchCondition condition);

	// 전문가 상세 조회
	List<SpecialistInfoView> selectBySpecialistId(String specialistId);

}
