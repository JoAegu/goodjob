package com.goodjob.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goodjob.blacklist.model.BlackListDAO;
import com.goodjob.companymember.model.CompanyMemberDAO;
import com.goodjob.companymember.model.CompanyMemberDTO;
import com.goodjob.member.model.MemberDAO;
import com.goodjob.member.model.MemberDTO;
import com.goodjob.module.EmailServiceImpl;
import com.goodjob.module.Module;
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
	private String hostName="http://192.168.1.142:9090/goodjob";
	
	@RequestMapping(value = "join.do", method = RequestMethod.GET)
	public String join() {
		return "/member/join";
	}
	

	@RequestMapping(value = "normalJoin.do", method = RequestMethod.POST)
	public ModelAndView normalJoin(NormalMemberDTO norDto, String birth_s) {
		norDto.setBirth(Module.datePasing(birth_s));
		ModelAndView mav = new ModelAndView();
		MemberDTO memDto = new MemberDTO(0, norDto.getId(), norDto.getPwd(), norDto.getName(), norDto.getEmail(),
				norDto.getTel(), norDto.getAddr(), null, 0, "개인", "대기");
		int idx = memDao.memberJoin(memDto);
		if (idx <= 0) {
			if (idx == -1) {
				mav.addObject("msg", "중복된 아이다가 있습니다 다시 시도해주세요");
			} else {
				mav.addObject("msg", "중복된 이메일이 있습니다 다시 시도해주세요");
			}
			mav.addObject("href","location.href='memberJoin.do';");
		} else {
			norDto.setMember_idx(idx);
			int count = norDao.normalJoin(norDto);
			if (count >= 1) {
				EmailServiceImpl.sendEmail (norDto.getEmail(), "goodjob회원가입 인증", hostName+"/updateStatus.do?idx="+idx+"&email="+norDto.getEmail());
				mav.addObject("msg", "가입이 완료되었습니다 이메일 인증해주세요");
				mav.addObject("href","location.href='index.do';");
			} else {
				mav.addObject("msg", "서버오류 고객센터로 문의해주세요");
				mav.addObject("href","location.href='memberJoin.do';");
			}
		}
		mav.setViewName("alertModal");
		return mav;
	}

	@RequestMapping(value = "comJoin.do", method = RequestMethod.POST)
	public ModelAndView comJoin(CompanyMemberDTO comDto, String birth_s) {
		comDto.setCom_birth(Module.datePasing(birth_s));
		System.out.println(comDto.toString());
		ModelAndView mav = new ModelAndView();
		MemberDTO memDto = new MemberDTO(0, comDto.getId(), comDto.getPwd(), comDto.getName(), comDto.getEmail(),
				comDto.getTel(), comDto.getAddr(), null, 0, "기업", "대기");
		int idx = memDao.memberJoin(memDto);
		if (idx <= 0) {
			if (idx == -1) {
				mav.addObject("msg", "중복된 아이다가 있습니다 다시 시도해주세요");
			} else {
				mav.addObject("msg", "중복된 이메일이 있습니다 다시 시도해주세요");
			}
			mav.addObject("href","location.href='memberJoin.do';");
		} else {
			comDto.setMember_idx(idx);
			int count = comDao.comJoin(comDto);
			if (count >= 1) {
				EmailServiceImpl.sendEmail (comDto.getEmail(), "goodjob회원가입 인증",hostName+"/updateStatus.do?idx="+idx+"&email="+comDto.getEmail());
				mav.addObject("msg", "가입이 완료되었습니다 이메일 인증해주세요");
				mav.addObject("href","location.href='index.do';");
			} else {
				mav.addObject("msg", "서버오류 고객센터로 문의해주세요");
				mav.addObject("href","location.href='memberJoin.do';");
			}
		}
		mav.setViewName("alertModal");
		return mav;
	}

	@RequestMapping(value = "updateMember.do", method = RequestMethod.GET)
	public ModelAndView updateMember(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Integer idx = session.getAttribute("sidx") == null ? 0 : (Integer) session.getAttribute("sidx");
		if (idx == 0) {
			mav.addObject("msg","로그인후 이용가능합니다");
			mav.addObject("href","location.href='login.do';");
			mav.setViewName("alertModal");
		} else {
			String category = session.getAttribute("scategory") == null ? "": (String) session.getAttribute("scategory");
			if (category.equals("개인")) {
				mav.addObject("dto", norDao.getNorMember(idx));
			} else if (category.equals("기업")) {
				mav.addObject("dto", comDao.getComMember(idx));
			}
			mav.setViewName("member/updateMember");
		}
		
		return mav;
	}

	@RequestMapping(value = "normalUpdate.do", method = RequestMethod.POST)
	public ModelAndView normalUpdate(NormalMemberDTO dto) {
		ModelAndView mav = new ModelAndView();
		MemberDTO mdto=new MemberDTO(dto.getMember_idx(), "", dto.getPwd(), dto.getName(), "", dto.getTel(), dto.getAddr(), null, 0, "", "");
		int membercount=memDao.memberUpdate(mdto);
		int count=norDao.norUpdate(dto);
		if(membercount>0&&count>0) {
			mav.addObject("msg","수정 완료되었습니다");
		}else {
			mav.addObject("msg","수정 실패");
		}
		mav.addObject("href", "location.href='updateMember.do';");
		mav.setViewName("alertModal");
		return mav;
	}

	@RequestMapping(value = "comUpdate.do", method = RequestMethod.POST)
	public ModelAndView comUpdate(CompanyMemberDTO dto) {
		ModelAndView mav = new ModelAndView();
		MemberDTO mdto=new MemberDTO(dto.getMember_idx(), "", dto.getPwd(), dto.getName(), "", dto.getTel(), dto.getAddr(), null, 0, "", "");
		int membercount=memDao.memberUpdate(mdto);
		int count=comDao.comUpdate(dto);
		if(membercount>0&&count>0) {
			mav.addObject("msg","수정 완료되었습니다");
		}else {
			mav.addObject("msg","수정 실패");
		}
		mav.addObject("href", "location.href='updateMember.do';");
		mav.setViewName("alertModal");
		return mav;
	}

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public ModelAndView login(@CookieValue(value = "sid", defaultValue = "") String id,
			@CookieValue(value = "sca", defaultValue = "개인") String user_category) {
		ModelAndView mav = new ModelAndView();
		if (!id.equals("")) {
			mav.addObject("check", "checked");
		}
		mav.setViewName("/member/login");
		if (user_category.equals("기업")) {
			mav.addObject("url", "comLogin.do");
			mav.addObject("script","$('.nor').attr('class','w-100 nor');$('.com').attr('class','w-100 bg-primary com');");
		} else {
			mav.addObject("url", "normalLogin.do");
		}
		return mav;
	}

	@RequestMapping(value = "normalLogin.do", method = RequestMethod.GET)
	public ModelAndView norMalLogin(String id, String pwd, HttpServletRequest req, boolean save,
			HttpServletResponse res) {
		MemberDTO dto = memDao.login(id, pwd, "개인");
		
		return loginSession(dto, req, save, res);
	}

	@RequestMapping(value = "comLogin.do", method = RequestMethod.GET)
	public ModelAndView comLogin(String id, String pwd, HttpServletRequest req, boolean save, HttpServletResponse res) {
		MemberDTO dto = memDao.login(id, pwd, "기업");

		return loginSession(dto, req, save, res);
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:index.jsp";
	}

	public ModelAndView loginSession(MemberDTO dto, HttpServletRequest req, boolean save, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView();
		if (dto == null) {
			mav.addObject("msg", "등록된 아이디 혹은 비밀번호가 없습니다");
			mav.addObject("href","location.href='login.do';");
			
		} else {
			Cookie ck = new Cookie("sid", dto.getId());
			Cookie ck2 = new Cookie("sca", dto.getUser_category());
			if (save) {
				ck.setMaxAge(3000);
				ck2.setMaxAge(3000);
			} else {
				ck.setMaxAge(0);
				ck2.setMaxAge(0);
			}
			res.addCookie(ck);
			res.addCookie(ck2);
			HttpSession session = req.getSession();
			session.setAttribute("sidx", dto.getIdx());
			session.setAttribute("sname", dto.getName());
			session.setAttribute("scategory", dto.getUser_category());
			session.setAttribute("status", dto.getStatus());
			session.setAttribute("semail",dto.getEmail());
			if(dto.getUser_category().equals("기업")) {
				CompanyMemberDTO cdto=comDao.comInfo(dto.getIdx());
				session.setAttribute("com_name", cdto.getCom_name());
			}
			mav.addObject("stop","location.href='index.do'");
			
		}
		mav.setViewName("/alertModal");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("findId.do")
	public int memberFindId(@RequestParam(value="email",defaultValue = "")String email) {
		String id=memDao.findId(email);
		int count=0;
		if(id != null && !id.isEmpty()) {
			EmailServiceImpl.sendEmail(email,"goodjob아이디찾기","고객님의 아이디는 "+id+" 입니다");	
			count=1;
		}
		return count;
	}
	
	@ResponseBody
	@RequestMapping("findPwd.do")
	public int memberFindPwd(@RequestParam(value="id",defaultValue = "")String id,
			@RequestParam(value="email",defaultValue = "")String email) {
		int idx=0;
		int count=0;
		idx=memDao.findPwd(id, email);
		if(idx>0) {
			EmailServiceImpl.sendEmail(email, "goodjob비밀번호찾기",
					"goodjob비밀번호 찾기입니다 url를 통해 비밀번호를 변경해주세요 "+hostName+"/pwdUpdate.do?idx="+idx);
			count=1;
		}
		return count;
	}

	@ResponseBody
	@RequestMapping("check.do")
	public int check(@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "email", defaultValue = "") String email) {
		if ((!id.equals("")) && (!id.equals(null))) {
			return memDao.idCheck(id);
		} else if ((!email.equals("")) && (!email.equals(null))) {
			return memDao.emailCheck(email);
		} else {
			return 0;
		}
	}
	@RequestMapping("updateStatus.do")
	public ModelAndView updateStatus(MemberDTO dto) {
		ModelAndView mav=new ModelAndView();
		int idx=dto.getIdx()==0?0:dto.getIdx();
			String email=dto.getEmail()==null?"":dto.getEmail();
			String msg=null;
			if(idx==0||email.length()==0) {
				msg="잘못된 접근입니다";
			}else {
				msg=memDao.updateStatus(dto)>0?"인증 완료되었습니다":"만료된 인증입니다";
			}
		mav.addObject("msg",msg);
		mav.addObject("href", "location.href='index.do'");
		mav.setViewName("alertModal");
		return mav;
	}
	@RequestMapping(value="pwdUpdate.do",method = RequestMethod.GET)
	public ModelAndView pwdUpdateForm(@RequestParam(value="idx",defaultValue = "0")int idx) {
		ModelAndView mav=new ModelAndView();
		mav.addObject("idx", idx);
		mav.setViewName("member/pwdUpdate");
		return mav;
	}
	@RequestMapping(value="pwdUpdate.do",method = RequestMethod.POST)
	public ModelAndView pwdUpdate(@RequestParam(value="idx",defaultValue = "0")int idx,
			@RequestParam(value="pwd",defaultValue = "")String pwd) {
		ModelAndView mav=new ModelAndView();
		int count=memDao.updatePwd(pwd, idx+"");
		if(count>0) {
			mav.addObject("msg","비밀번호가 변경되었습니다");
		}else {
			mav.addObject("msg","변경 실패하였습니다");
		}
		mav.addObject("href","location.href='index.do'");
		mav.setViewName("alertModal");
		return mav;
	}
	@RequestMapping(value="kakaoJoin.do")
	public String kakaoJoin() {
		return "member/kakaoJoin";
	}

}
