package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// controller 실행하기 직전에 실행됨
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		String[] uriArr = uri.split("/");
		
		System.out.println("uriArr.length : " + uriArr.length);
		if(uri.equals("/")) {  // 리소스 (js, img, css) 일차 주솟값이 res면 바로 통과
			return true;
		} else if(uriArr[1].equals("res")) {
			return true;
		}
		
		System.out.println("인터셉터!!");
		boolean isLogout = SecurityUtils.isLogout(request);
		
		
		switch(uriArr[1]) {
		case ViewRef.URI_USER:  //user
			switch(uriArr[2]) {
			case "login" : case "join" :
				if(!isLogout) { // 로그인 되어 있는 상태
					response.sendRedirect("/rest/map");
					return false;
				}
			}
		case ViewRef.URI_REST:  //rest
			switch(uriArr[2]) {
			case "restReg" :
				if(isLogout) { // 로그아웃 상태
					response.sendRedirect("/user/login");
					return false;
				}
			}
		}
		
		return true; 
	}
}
