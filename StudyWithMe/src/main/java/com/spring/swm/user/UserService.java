package com.spring.swm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.swm.Const;
import com.spring.swm.SecurityUtils;
import com.spring.swm.user.model.UserVO;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;

	public int join(UserVO param) {
		String pw = param.getUser_pw();
		
		if(pw == null) {
			return Const.NULL_PW;
		}
		
		String salt = SecurityUtils.generateSalt(); 
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		System.out.println("sdafasd" + param.getUser_email());
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		return mapper.insUser(param);
	}

	public int login(UserVO param) {
		//1번 로그인 성공, 2번 아이디 없음, 3번 비번 틀림
		System.out.println(param.getUser_email());
		if(param.getUser_email().equals("")) {
			return Const.NO_EMAIL;
		}
		UserVO dbUser = mapper.selUser(param);
		
		if(dbUser == null) {
			return Const.NO_EMAIL;
		}
		
		String cryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), dbUser.getSalt());
		if(!cryptPw.equals(dbUser.getUser_pw())) {
			return Const.NO_PW;
		}
		
		param.setUser_pw(null);
		param.setNm(dbUser.getNm());
		param.setI_user(dbUser.getI_user());
		return Const.SUCCESS;
	}

	public UserVO confirm(UserVO vo) {
		return mapper.selEmail(vo);
	}
}