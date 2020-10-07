package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired // 각 상황의 타입에 맞는 IoC컨테이너 안에 존재하는 객체화된 Bean을 자동으로 주입해 줌
						// 단 하나만 등록이 돼있어야함
	private UserService service;
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT; // ViewResolver (서블릿 -> jsp)
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(UserPARAM param, HttpSession hs, RedirectAttributes rs) { 
		//세션 주는 법 : HttpSession hs
		int result = service.login(param);
		
		if(result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/"; // response.sendRedirect() 서블릿 
			// --> 서블릿(rest/map GET메소드로 감)
		}
		
		String msg = null;
		if(result == Const.NO_ID) {
			msg = "아이디를 확인해 주세요.";
		} else if(result == Const.NO_PW) {
			msg = "비밀번호를 확인해 주세요";
		}
		
		param.setMsg(msg);
		rs.addFlashAttribute("data", param); // 세션에 박히고 쓰고 나면 알아서 지움
		return "redirect:/user/login";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(defaultValue="0") Integer err) {
		// join() 파라미터 안에 ,찍고 @RequestParam() 안에
		System.out.println("err : " + err);
		
		if(err > 0) {
			model.addAttribute("msg", "에러가 발생하였습니다.");
		}
		
		model.addAttribute(Const.TITLE, "회원가입");
		model.addAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(UserVO param, RedirectAttributes rs) {
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		
		rs.addFlashAttribute("err", result);
		return "redirect:/user/join";
	}
	
	@RequestMapping(value="/ajaxIdChk", method = RequestMethod.POST)
	@ResponseBody // 이것이 없다면 jsp 파일을 찾았을 것이다 // 얘가 적혀 있으면 값을 return해 줌
	public String ajaxIdChk(@RequestBody UserPARAM param) {
		int result = service.login(param);
		
		
		return String.valueOf(result); // 리턴에 redirect가 없으면 그값 자체를 응답
	}
	
	@RequestMapping(value="/ajaxToggleFavorite", method = RequestMethod.GET)
	@ResponseBody // 이것이 없다면 jsp 파일을 찾았을 것이다 // 얘가 적혀 있으면 값을 return해 줌
	public int ajaxToggleFavorite(UserPARAM param, HttpSession hs) {
		// ajax를 get방식으로 받을 때에는 @RequestBody를 없애야 한다 
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);		
		
		return service.ajaxToggleFavorite(param); // 리턴에 redirect가 없으면 그값 자체를 응답
	}
	
	@RequestMapping(value="/favorite")
	public String favorite(Model model, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPk(hs);
		UserPARAM param = new UserPARAM();
		param.setI_user(i_user);
		
		model.addAttribute("data", service.selFavoriteList(param));
		
		model.addAttribute(Const.CSS, new String[] {"userFavorite"});
		model.addAttribute(Const.TITLE, "찜 리스트");
		model.addAttribute(Const.VIEW, "user/favorite");
		return ViewRef.TEMP_MENU_TEMP;
	}
}
