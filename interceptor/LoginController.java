package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.myapp.common.SharedScopeKeys;
import org.zerock.myapp.domain.LoginDTO;
import org.zerock.myapp.domain.UserVO;
import org.zerock.myapp.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
//@NoArgsConstructor // 직접주입
@AllArgsConstructor  // 자동주입

@RequestMapping("/user")
@Controller
public class LoginController {
	
	// 직접 주입
//	@Setter(onMethod_=@Autowired)
	private UserService service;
	
	@PostMapping("/loginPost")
	public String loginPost(LoginDTO dto, Model model, RedirectAttributes rttrs) throws Exception {
		log.info("loginPost() invoked.");
		
		UserVO vo = this.service.findUser(dto);
		
		if(vo != null) {     // 로그인에 성공
			// 모델 상자 안에 VO 객체를 넣어주고 자동으로 Command Object도 담아서 뷰(JSP)로 전달
			model.addAttribute(SharedScopeKeys.LOGIN_KEY, vo);
			
			return "/user/loginPost";
			
		} else {             // 로그인에 실패
			// 로그인 실패 원인을 보여주면서 로그인 화면으로 다시 재이동 시키기
			rttrs.addFlashAttribute(SharedScopeKeys.LOGIN_RESULT, "Login Failed"); // 1회성 전달			
			
			return "redirect:/user/login";
			
		} // if-else
		
	} // loginPost
	

	// servler-context의  Spring <mvc:-view controller> 태그로 대체
//	@GetMapping("/login")
//	public void login() {
//		log.trace("login() invoked.");
//		
//	} // login
	

	// 로그아웃 로직처리는 모두 interceptor에서 해야한다
	// LogoutInterceptor를 적용할 목적으로만 사용되고, 실제 요청을 처리하지 못하는 핸들러
	@GetMapping("/logout")
	public void dummyLogout() {
		log.trace("dummyLogout() invoked.");
		
	} // dummyLogout
	
} // end class
