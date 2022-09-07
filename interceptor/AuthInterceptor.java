package org.zerock.myapp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.zerock.myapp.common.SharedScopeKeys;
import org.zerock.myapp.domain.UserVO;
import org.zerock.myapp.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
//@NoArgsConstructor
@AllArgsConstructor // 자동주입

// Authorization을 체크하는 역할로 회원만이 사용가능한 요청에 대해 가장 기본적인 권한으로 "인증여부" 체크
@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	// 주입	
	private UserService service; // 자동주입

	
	// 1. HTTP Request가 Controller의 Handler method로 위임되기 직전에 콜백
	
	// 컨트롤러의 핸들러 메소드로 요청이 위임되기 직전에, 기본적인 권한 체크(인증여부)를 수행
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("1. preHandle(req, res, {}) invoked.", handler);
		
		// Step 1. 현재 요청을 보낸 웹브라우저에 대응되는 세션을 획득
		HttpSession session = req.getSession();
		log.info("\t+ 1. session Id : {}", session.getId());
		
		// Step 2. Session Scope에 인증정보 (즉, UserVO 객체)가 없으면 => 로그인 창으로 Re-direct하고
        //                                                      있다면 => 요청을 그대로 컨트롤러로 전달
		UserVO vo = (UserVO) session.getAttribute(SharedScopeKeys.USER_KEY);
		
		if(vo != null) {   // 인증된 사용자!
			log.info("\t+ 2. Authentication Found : {}", vo);
			
			return true;   // 컨트롤러로 요청 위임
		} else {           // 인증 안 된 사용자!
			
			// ============================== //
			// 자동로그인 (Remember-me) 처리 
			// ============================== //
			// 필요한 처리 2가지
			//  1) 자동로그인 쿠키가 들어온 웹브라우저라면, 이 쿠키값으로 tbl_user 테이블을 조회하고
			//     그 결과 특정 user 정보가 획득되고 이 정보로 인증 객체인 UserVO 객체를 생성해서 Session Scope에 바인딩 시킴
			//  2) 1에 의해서 Session Scope에 UserVO가 있다면 무사통과 시켜줘야 함
			
			// Step 1. 현재 요청을 보내온 웹 브라우저의 요청메시지의 헤더(Cookie)에 자동로그인 쿠키를 획득
			Cookie rememberMeCookie = WebUtils.getCookie(req, SharedScopeKeys.REMEMBERME_KEY);
			
			if(rememberMeCookie !=null) { // 자동로그인 처리 대상 웹브라우저가 된다 => 인증정보 (UserVO 객체)를 복구
				
				// Step 2. step1에서 얻어낸 자동로그인 쿠키 값으로 인증객체인 UserVO객체를 복구하고 
				// 이를 Session Scope에 바인딩 => 인증복구
				String cookieName = rememberMeCookie.getName();
				String cookieValue = rememberMeCookie.getValue(); // 자동로그인 세션ID (이전에 죽었던 웹 브라우저의 세션ID)
				log.info("\t+ 2. cookieName : {}, cookieValue : {}", cookieName, cookieValue);
				
				// Step 3.  웹 브라우저가 보내온 자동로그인 쿠키값으로 인증정보 객체 UserVO를 획득
				UserVO repairedUserVO = this.service.findUSerByRememberMeCookie(cookieValue);
				log.info("\t+ 2. repairedUserVO : {}", repairedUserVO);
				
				// Step 4. 현재의 웹브라우저의 할당된 Session Scope에 인증정보객체 (UserVO)를 바인딩 => 인증복구
				session.setAttribute(SharedScopeKeys.USER_KEY, repairedUserVO);
				
				if(repairedUserVO != null) return true; // 컨트롤러로 요청을 위임하지 않음 => 즉, 무사통과
			} // if
			
			log.info("\t+ 2. No Authentication Found.");
		
			// 자동로그인 옵션이 켜져있는 웹브라우저라면 로그인 창으로 리다이렉트하면 안 됨
			res.sendRedirect("/user/login");  // 로그인 창으로 Re-direct
			
			return false; // 컨트롤러로 요청을 위임하지 않음
		} // if-else
		
	} // preHandle
	
	
	// 2. Controller의 Handler method가 수행 완료된 직후에 콜백
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,	ModelAndView modelAndView) throws Exception {
		log.trace("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		
		
		
	} // postHandle

	
	// 3. Spring MVC의 View가 호출 완료된 직후에 콜백
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception e) throws Exception {
		log.trace("3. afterCompletion(req, res, {}, {}) invoked.", handler, e);
		
				
	} // afterCompletion

} // end class
