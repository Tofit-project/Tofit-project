package com.tofit.mvc.model.dto;

public class SearchCondition {
	private String keyWord;
	
	public SearchCondition() {}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Override
	public String toString() {
		return "SearchCondition [keyWord=" + keyWord + "]";
	}
	
	
}
