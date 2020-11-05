package com.spring.swm.stopwatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.swm.Const;
import com.spring.swm.ViewRef;
import com.spring.swm.stopwatch.model.TimeVO;


@Controller
@RequestMapping("/swm")
public class StopWatchController {
	
	@Autowired
	private StopWatchService service;
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main(Model model) {
		model.addAttribute(Const.CSS, new String[] {"mainCss"});
		model.addAttribute(Const.TITLE, "Study With Me");
		model.addAttribute(Const.VIEW, "swm/main");
		
		return ViewRef.TEMP_DEFAULT; // jsp로 
	}
	
	@RequestMapping(value="/start", method=RequestMethod.GET)
	public String start(Model model) {
		model.addAttribute(Const.TITLE, "Studying");
		model.addAttribute(Const.VIEW, "swm/stopwatch");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/start", method=RequestMethod.POST)
	public String start(TimeVO vo) {
		int result = service.insStartTime(vo);
		System.out.println(result);
		return "redirect:/swm/start";
	}
	
	@RequestMapping(value="/stop", method=RequestMethod.POST)
	public String stop(TimeVO vo, RedirectAttributes rs) {
		int result = service.insEndTime(vo);
		
		if(result == Const.TIME_ON) {
			return "redirect:/swm/main";
		}
		
		String msg = null;
		if(result == Const.NO_START) {
			msg = "스톱워치를 시작하지 않았습니다.";
		}
		
		vo.setMsg(msg);
		//rs.addFlashAttribute("data", vo);
		return "redirect:/swm/main";
	}
	
}
