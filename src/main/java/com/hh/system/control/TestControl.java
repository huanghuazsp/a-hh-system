package com.hh.system.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("system")
public class TestControl {
	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView modelAndView = new ModelAndView("classpath:ftl/com/hh/system/Test/test.ftl");
		return modelAndView;
	}
}
