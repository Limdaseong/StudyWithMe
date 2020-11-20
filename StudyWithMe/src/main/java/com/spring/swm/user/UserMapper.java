package com.spring.swm.user;

import org.apache.ibatis.annotations.Mapper;

import com.spring.swm.user.model.UserVO;

@Mapper
public interface UserMapper {
	public int insUser(UserVO param); // 회원가입
	public int insKakaoUser(UserVO vo); // 카카오 회원가입
	
	public UserVO selUser(UserVO param); // 회원정보 가져오
	public UserVO selEmail(UserVO vo); // 이메일 확인
	public int emailChk(UserVO vo);

	
}
