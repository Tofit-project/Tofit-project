package com.tofit.mvc.model.dto;

public class Category {
	private int code;
	private String goal;
	
	public Category(int code, String goal) {
		super();
		this.code = code;
		this.goal = goal;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	@Override
	public String toString() {
		return "Category [code=" + code + ", goal=" + goal + "]";
	}
	
	
	
}
