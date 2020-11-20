package com.spring.swm.user;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.swm.Const;
import com.spring.swm.ViewRef;
import com.spring.swm.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired private UserService service;
	@Autowired private Kakao_restapi kakao;
	
	@RequestMapping(value="/joinAndLogin", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(defaultValue="0") Integer err) {
		if(err > 0) {
			model.addAttribute("msg", "에러가 발생하였습니다.");
		}
		
		model.addAttribute(Const.CSS, new String[] {"joinAndLogin"});
		model.addAttribute(Const.TITLE, "회원가입");
		model.addAttribute(Const.VIEW, "user/joinAndLogin");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(UserVO param, RedirectAttributes rs) {
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/swm/main";
		}
		
		rs.addFlashAttribute("err", result);
		return "redirect:/user/joinAndLogin";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(UserVO param, RedirectAttributes rs, HttpSession session) {
		int result = service.login(param);
		
		if(result == Const.SUCCESS) {
			session.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/studygram/home"; // response.sendRedirect() 서블릿 
			// --> 서블릿(rest/map GET메소드로 감)
		}
		
		String msg = null;
		if(result == Const.NO_EMAIL) {
			msg = "아이디를 확인해 주세요.";
			return "redirect:/user/joinAndLogin";
		} else if(result == Const.NO_PW) {
			msg = "비밀번호를 확인해 주세요";
			return "redirect:/user/joinAndLogin";
		}
		
		param.setMsg(msg);
		rs.addFlashAttribute("data", param);
		
		return "redirect:/studygram/home";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/swm/main";
	}
	
	@GetMapping("/kakaoLogout")
	public String kakaoLogout(HttpSession session) {
	    kakao.kakaoLogout((String)session.getAttribute("access_Token"));
	    session.removeAttribute("access_Token");
	    session.removeAttribute("userId");
	    return "redirect:/swm/main";
	}
	
	@RequestMapping(value="/ajaxIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxEmailChk(@RequestBody UserVO vo) {
		int result = service.emailChk(vo);
		return String.valueOf(result);
	}
	
	@RequestMapping(value="/oauth")
	public String kakaoLogin(@RequestParam("code") String code, HttpSession session, UserVO vo) throws Exception {
		System.out.println("code : "+code);
		String access_Token = kakao.getAccessToken(code);
		HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
		System.out.println("login Controller : " + userInfo);
		System.out.println(userInfo.get("email"));
		vo.setUser_email((String)userInfo.get("email"));
		int email = service.emailChk(vo);
		
		if(email == 0) {
			vo.setNm((String)userInfo.get("nickname"));
			vo.setProfile_img((String)userInfo.get("profile_img"));
			service.kakaoJoin(vo);
			return "redirect:/swm/main";
		}
		
		if(userInfo.get("email") != null) {
			session.setAttribute("userId", userInfo.get("email"));
			session.setAttribute("nickname", userInfo.get("nickname"));
			session.setAttribute("access_Token", access_Token);
		}
		return "redirect:/swm/main";
	}
}
