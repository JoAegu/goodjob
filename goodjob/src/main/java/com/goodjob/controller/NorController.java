package com.goodjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NorController {
	
	@RequestMapping("/nor.do")
	public String mypage() {
		return "norMyPage/nor.jsp";
	}
	
	
}