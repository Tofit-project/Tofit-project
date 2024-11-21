package com.tofit.mvc.model.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tofit.mvc.model.dto.SearchCondition;
import com.tofit.mvc.model.dto.SpecialistInfoView;
import com.tofit.mvc.model.service.SpecialistService;

@RestController
@RequestMapping("/tofit/specialist")
public class SpecialistRestController {

	private final SpecialistService specialistService;

	public SpecialistRestController(SpecialistService specialistService) {
		this.specialistService = specialistService;
	}
	
	// 전문가 조건 조회
	@GetMapping()
	public ResponseEntity<?> list(@ModelAttribute SearchCondition condition){
		List<SpecialistInfoView> list = specialistService.search(condition);
		
		if(list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<SpecialistInfoView>>(list, HttpStatus.OK);
		
	}
	
	// 전문가 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable("id") String specialistId){
		
		List<SpecialistInfoView> list = specialistService.getDetail(specialistId);
		
		if(list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<List<SpecialistInfoView>>(list, HttpStatus.OK);
	}
}
