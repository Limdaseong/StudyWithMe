package com.spring.swm.user.model;

import org.springframework.web.multipart.MultipartFile;

public class UserVO {
	private int i_user; // 회원 번호
	private String user_email; // 회원 이메일
	private String user_pw; // 회원 비밀번호
	private String nm; // 회원 닉네임
	private String salt; // 회원 비밀번호 암호화
	private String r_dt; // 회원 가입날짜
	private String m_dt; //	수정날짜
	private String msg; // 메시지
	private String profile_img; // 프로필 이미지

	private MultipartFile file;
	private int userImgNo;
	private String profileName; //파일 이름
	private long profileSize; //파일 크기
	private String profileContentType; //파일 타입
	
	public int getI_user() {
		return i_user;
	}
	public void setI_user(int i_user) {
		this.i_user = i_user;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
	public String getM_dt() {
		return m_dt;
	}
	public void setM_dt(String m_dt) {
		this.m_dt = m_dt;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public int getUserImgNo() {
		return userImgNo;
	}
	public void setUserImgNo(int userImgNo) {
		this.userImgNo = userImgNo;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public long getProfileSize() {
		return profileSize;
	}
	public void setProfileSize(long profileSize) {
		this.profileSize = profileSize;
	}
	public String getProfileContentType() {
		return profileContentType;
	}
	public void setProfileContentType(String profileContentType) {
		this.profileContentType = profileContentType;
	}
}
