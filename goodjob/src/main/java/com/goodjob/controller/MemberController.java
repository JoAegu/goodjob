package com.goodjob.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.goodjob.companymember.model.CompanyMemberDAO;
import com.goodjob.companymember.model.CompanyMemberDTO;
import com.goodjob.member.model.MemberDAO;
import com.goodjob.member.model.MemberDTO;
import com.goodjob.normalmember.model.NormalMemberDAO;
import com.goodjob.normalmember.model.NormalMemberDTO;

@Controller
public class MemberController {
	@Autowired
	private NormalMemberDAO norDao;
	@Autowired
	private CompanyMemberDAO comDao;
	@Autowired
	private MemberDAO memDao;

	@RequestMapping(value = "join.do", method = RequestMethod.GET)
	public String join() {
		return "/member/join";
	}

	@RequestMapping(value = "normalJoin.do", method = RequestMethod.POST)
	public ModelAndView normalJoin(NormalMemberDTO norDto, String birth_s) {
		norDto.setBirth(datePasing(birth_s));
		ModelAndView mav = new ModelAndView();
		MemberDTO memDto = new MemberDTO(0, norDto.getId(), norDto.getPwd(), norDto.getName(), norDto.getEmail(),
				norDto.getTel(), norDto.getAddr(), null, 0, "개인", "활성");
		int idx = memDao.memberJoin(memDto);
		if (idx == 0) {
			mav.addObject("msg", "회원가입실패");
			mav.setViewName("/member/join");
		} else {
			norDto.setMember_idx(idx);
			norDao.normalJoin(norDto);
			mav.addObject("msg", "가입성공");
			mav.setViewName("index");
		}
		return mav;
	}

	@RequestMapping(value = "comJoin.do", method = RequestMethod.POST)
	public ModelAndView comJoin(CompanyMemberDTO comDto, String birth_s) {
		comDto.setCom_birth(datePasing(birth_s));
		ModelAndView mav = new ModelAndView();
		MemberDTO memDto = new MemberDTO(0, comDto.getId(), comDto.getPwd(), comDto.getName(), comDto.getEmail(),
				comDto.getTel(), comDto.getAddr(), null, 0, "기업", "활성");
		int idx = memDao.memberJoin(memDto);
		if (idx == 0) {
			mav.addObject("msg", "회원가입실패");
			mav.setViewName("/member/join");
		} else {
			comDto.setMember_idx(idx);
			comDao.comJoin(comDto);
			mav.addObject("msg", "가입성공");
			mav.setViewName("index");
		}
		return mav;
	}

	@RequestMapping(value = "updateMember.do", method = RequestMethod.GET)
	public ModelAndView updateMember(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		int idx=Integer.parseInt((String)session.getAttribute("sidx")); 
		String category=(String)session.getAttribute("scategory");
		if(category.equals("개인")) {
			mav.addObject("dto",norDao.getNorMember(idx));
		}else if(category.equals("기업")){
			mav.addObject("dto",comDao.getComMember(idx));
		}
		mav.setViewName("member/updateMember");
		return mav;
	}

	@RequestMapping(value = "normalUpdate.do", method = RequestMethod.POST)
	public ModelAndView normalUpdate(NormalMemberDTO dto) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	@RequestMapping(value = "comUpdate.do", method = RequestMethod.POST)
	public ModelAndView comUpdate(CompanyMemberDTO dto) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String login() {
		return "/member/login";
	}

	@RequestMapping(value = "normalLogin.do", method = RequestMethod.GET)
	public ModelAndView norMalLogin(String id, String pwd, HttpServletRequest req) {
		MemberDTO dto = memDao.login(id, pwd, "개인");
		return loginSession(dto, req);
	}

	@RequestMapping(value = "comLogin.do", method = RequestMethod.GET)
	public ModelAndView comLogin(String id, String pwd, HttpServletRequest req) {
		MemberDTO dto = memDao.login(id, pwd, "기업");
		return loginSession(dto, req);
	}

	public ModelAndView loginSession(MemberDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		if (dto == null) {
			mav.addObject("msg", "등록된아이디혹은비밀번호가 없습니다");
			mav.setViewName("/member/login");
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("sidx", dto.getIdx());
			session.setAttribute("sname", session);
			session.setAttribute("scategory", session);
			mav.setViewName("index");
		}
		return mav;
	}
	public Date datePasing(String birth_s) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date date = formatter.parse(birth_s);
			java.sql.Date birth = new java.sql.Date(date.getTime());
			return birth;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
