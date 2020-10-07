package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Controller //handlermapper
@RequestMapping("/rest") // handlermapper에서 첫번째 칸 /
public class RestController {
	
	@Autowired
	private RestService service;

	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE, "지도보기");
		model.addAttribute(Const.VIEW, "rest/map");
		
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/ajaxGetList", produces = "application/json; charset=utf8") // 마커 인코딩 설정
	@ResponseBody public List<RestDMI> ajaxGetList(RestPARAM param, HttpSession hs) {
		// 객체를 반환하려면 ResponseBody 필요함
//		System.out.println("sw_lat : " + param.getSw_lat());
//		System.out.println("sw_lng : " + param.getSw_lng());
//		System.out.println("ne_lat : " + param.getNe_lat());
//		System.out.println("ne_lng : " + param.getNe_lng());
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		// 찜했는지 안했는지 알기 위해 i_user 값 넣음
		return service.selRestList(param);
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String restReg(Model model) {
		model.addAttribute("categoryList", service.selCategoryList());
		model.addAttribute(Const.TITLE, "등록");
		model.addAttribute(Const.VIEW, "rest/reg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String restReg(RestPARAM param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPk(hs));
		// 로그인한 사람의 정보를 가져오려면 세션에서 받아오기
		
		int result = service.insRest(param);
		System.out.println(result);
		return "redirect:/"; //바꿨었넹
	}
	// 새로고침보다는 서버를 껐다가 다시 켜기
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, RestPARAM param, HttpServletRequest req) {
		service.addHits(param, req);
		
		int i_user = SecurityUtils.getLoginUserPk(req);
		param.setI_user(i_user);

		RestDMI data = service.selRest(param);	
		//List<RestRecMenuVO> item = service.selRestRecMenus(param);
		model.addAttribute("recMenuList", service.selRestRecMenus(param));
		model.addAttribute("menuList", service.selRestMenus(param));
		model.addAttribute("css", new String[] {"restaurant", "swiper-bundle.min"});
		model.addAttribute("data", data);
		model.addAttribute(Const.TITLE, data.getNm());
		model.addAttribute(Const.VIEW, "rest/detail");		
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/del")
	public String del(RestPARAM param, HttpSession hs) {
		int loginI_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(loginI_user);
		int result = 1;
		try {
			// try문을 안쓰면 쿼리문이 다 보인다
			service.delRestTran(param);
		} catch(Exception e) {
			e.printStackTrace();
			result = 0;
		}
		System.out.println("result : " + result);
		return "redirect:/";
	}
	
	@RequestMapping(value="/recMenus", method=RequestMethod.POST)
	public String recMenus(MultipartHttpServletRequest mReq
			, RedirectAttributes ra) {
		int i_rest = service.insRecMenus(mReq);
		
		ra.addAttribute("i_rest", i_rest);
		return "redirect:/rest/detail";
	}
	
	@RequestMapping(value="/ajaxDelRecMenu")
	@ResponseBody public int ajaxDelRecMenu(RestPARAM param, HttpSession hs) {
		String path = "/resources/img/rest/" + param.getI_rest() + "/rec_menu/";
		String realPath = hs.getServletContext().getRealPath(path);
		param.setI_user(SecurityUtils.getLoginUserPk(hs));
		return service.delRestRecMenu(param, realPath);
	}
	

	@RequestMapping(value="/ajaxDelMenu")
	@ResponseBody public int ajaxDelMenu(RestPARAM param) {
		// i_rest, seq, menu_pic이 
		return service.delRestMenu(param);
	}
	
	@RequestMapping("/menus")
	public String menus(@ModelAttribute RestFile param, 
		RedirectAttributes ra, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPk(hs);
		
		int result = service.insRestMenus(param, i_user);
		ra.addAttribute("i_rest", param.getI_rest());
		return "redirect:/rest/detail";
	}
	
	@RequestMapping("/ajaxSelMenuList")
	@ResponseBody
	public List<RestRecMenuVO> ajaxSelMenuList(RestPARAM param) {
		return service.selRestMenus(param);
	}
}
