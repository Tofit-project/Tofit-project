package com.tofit.mvc.model.service;

import java.util.List;

import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.Specialist;
import com.tofit.mvc.model.dto.SpecialistInfoView;

public interface SpecialistService {

    List<Specialist> search(SearchCondition condition);

    List<SpecialistInfoView> getDetail(String specialistId);

   List<SpecialistInfoView> getVideoInfo();

}