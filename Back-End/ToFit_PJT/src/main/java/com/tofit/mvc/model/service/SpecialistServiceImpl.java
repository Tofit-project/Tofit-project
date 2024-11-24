package com.tofit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tofit.mvc.model.dao.SpecialistDao;
import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.Specialist;
import com.tofit.mvc.model.dto.SpecialistInfoView;

@Service
public class SpecialistServiceImpl implements SpecialistService{
    
    private final SpecialistDao specialistDao;
    
    public SpecialistServiceImpl(SpecialistDao specialistDao) {
        this.specialistDao = specialistDao;
    }

    // 전문가 조건(이름) 검색
    @Override
    public List<Specialist> search(SearchCondition condition) {
        
        return specialistDao.selectByCondition(condition);
    }

    // 전문가가 올린 영상 정보 조회
    @Override
    public List<SpecialistInfoView> getVideoInfo() {
       
       return specialistDao.selectSpecialistVideo();
    }
    
    // 전문가 상세 조회
    @Override
    public List<SpecialistInfoView> getDetail(String specialistId) {
        return specialistDao.selectBySpecialistId(specialistId);
    }


}