package com.tofit.mvc.model.dao;

import java.util.List;

import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;

public interface SpecialistDao {

	List<SpecialistInfoView> selectByCondition(SearchCondition condition);

	List<SpecialistInfoView> selectBySpecialistId(String specialistId);

}
