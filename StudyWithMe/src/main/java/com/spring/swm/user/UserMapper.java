package com.spring.swm.user;

import org.apache.ibatis.annotations.Mapper;

import com.spring.swm.user.model.UserVO;

@Mapper
public interface UserMapper {
	public int insUser(UserVO param);

	public UserVO selUser(UserVO param);

	public UserVO selEmail(UserVO vo);
}
