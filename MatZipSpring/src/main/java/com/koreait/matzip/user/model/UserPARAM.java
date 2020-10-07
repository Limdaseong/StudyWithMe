package com.koreait.matzip.user.model;

public class UserPARAM extends UserVO {
	private String msg;
	private int i_rest;
	private int proc_type;

	public int getI_rest() {
		return i_rest;
	}

	public void setI_rest(int i_rest) {
		this.i_rest = i_rest;
	}

	public int getProc_tpe() {
		return proc_type;
	}

	public void setProc_tpe(int proc_tpe) {
		this.proc_type = proc_tpe;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
