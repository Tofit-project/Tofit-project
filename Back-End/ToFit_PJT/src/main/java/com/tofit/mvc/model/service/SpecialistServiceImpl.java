package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.SpecialistDao;
import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;

@Service
public class SpecialistServiceImpl implements SpecialistService{
	
	private final SpecialistDao specialistDao;
	
	public SpecialistServiceImpl(SpecialistDao specialistDao) {
		this.specialistDao = specialistDao;
	}

	@Override
	public List<SpecialistInfoView> search(SearchCondition condition) {
		
		return specialistDao.selectByCondition(condition);
	}

	@Override
	public List<SpecialistInfoView> getDetail(String specialistId) {
		return specialistDao.selectBySpecialistId(specialistId);
	}

}
