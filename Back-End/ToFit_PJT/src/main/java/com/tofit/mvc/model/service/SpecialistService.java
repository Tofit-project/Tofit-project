package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;

public interface SpecialistService {

	List<SpecialistInfoView> search(SearchCondition condition);

	List<SpecialistInfoView> getDetail(String specialistId);

}
