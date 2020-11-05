package com.spring.swm.stopwatch.model;

import com.spring.swm.user.model.UserVO;

public class TimeVO extends UserVO {
	private String start_time;
	private String end_time;
	private String msg;
	
	public String getStart_time() {
		return start_time;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
}
