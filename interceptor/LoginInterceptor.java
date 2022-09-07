package org.zerock.myapp.interceptor;

import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.myapp.common.SharedScopeKeys;
import org.zerock.myapp.domain.UserVO;
import org.zerock.myapp.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
//@NoArgsConstructor // 직접주입
@AllArgsConstructor  // 자동주입

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	
	// 주입
	private UserService service; // 자동주입

	
	// 1. HTTP Request가 Controller의 Handler method로 위임되기 직전에 콜백
	
	// Incoming Request가 Controller;s Handler Method로 위임되기 직전에 가로채는 부분
	// Session Scope에 저장되어 있던 모든 정보 파괴 수행
	// (주의) 명시적으로 로그아웃 요청을 보내지 않는 한 세션 자체를 파괴해서는 안 됨
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("1. preHandle(req, res, {}) invoked.", handler);
		
		// Step 1. Session Scope에 접근할 수 있는 HttpSession 객체를 획득
		HttpSession session = req.getSession(); // 현재 브라우저에 대해 세션이 있으면 그 세션을 돌려주고 없으면 새로 만든다
		log.info("\t+ 1. session Id : {}", session.getId());
		
		// Step 2. Session Scope에서 UserVO 인증객체를 파괴!
		session.removeAttribute(SharedScopeKeys.USER_KEY);	
		log.info("\t+ 2. UserVO shared object deleted");
	
		
		return true;
	} // preHandle
	
	
	// 2. Controller의 Handler method가 수행 완료된 직후에 콜백
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,	ModelAndView modelAndView) throws Exception {
		log.trace("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		
		// Step 1. Session Scope에 접근할 수 있는 HttpSession 객체를 획득
		HttpSession session = req.getSession();
		log.info("\t+ 1. session Id : {}", session.getId());
		
		// Step 2. 매개변수 modelAndView를 조사해서, UserVO가 들어있는지 확인하고
		//         있다면 UserVO를 인증객체로 Session Scope에 올려놓자
		ModelMap model = modelAndView.getModelMap();
		UserVO vo = (UserVO) model.get(SharedScopeKeys.LOGIN_KEY);
		
		if(vo != null) { // 로그인 성공
			//==========================================================//
			// (1) 세션영역에 인증객체인 UserVO를 바인딩 처리 - 인증수행 
			//==========================================================//
			session.setAttribute(SharedScopeKeys.USER_KEY, vo);
			log.info("\t+ 2. Authentication Succeed ");			
			
			
			
			//==========================================================//
			// (2) 자동로그인 옵션체크하여 on이면 자동로그인용 쿠키 생성 및 저장 
			//==========================================================//
			
			// Step 1. 자동로그인 옵션을 체크
			boolean isRememberMe = checkRememberMeOption(req);
			log.info("\t+ 3. isRememberMe : {}", isRememberMe);
			
			// Step 2. 자동로그인 쿠키 생성 및 쿠키의 만료 기간도 설정
			if(isRememberMe) {	// 자동로그인 옵션이 on일 경우 처리할 로직

				// 쿠키값 획득 : 현재 웹브라우저의 이름
				String sessionId = req.getSession().getId(); 
				log.info("\t+ 4. sessionId : {}", sessionId);
				
				// 쿠키 값 : 키 = 값 형태로 되어있음.
				// 자동로그인용 쿠키 값은 현재 인증에 성공한 웹브라우저의 이름 (즉, 세션 ID)로 지정
				Cookie rememberMeCookie = new Cookie(SharedScopeKeys.REMEMBERME_KEY, sessionId);
				
				final int maxAge = 1*60*60*24*7;
				rememberMeCookie.setMaxAge(maxAge); // 쿠키의 유효기간을 최대 1주일로 지정
				rememberMeCookie.setPath("/"); // 새로운 요청을 서버로 전송시 그 어떠한 요청 URI 발생시에도 쿠키값을 전송
				log.info("\t+ 5. rememberMeCookie : {}", rememberMeCookie);

				
				// Step3. step2에서 생성한 자동로그인 쿠키를 응답메시지의 헤더('Set-Cookie')에 저장
				res.addCookie(rememberMeCookie);
				
				// Step4. 자동로그인 쿠키의 값 + 만료일자 2개의 값을 tbl_user 테이블에 기록
				long now = System.currentTimeMillis();
				long expiry = now + (maxAge * 1000L); // 자동로그인 쿠키의 만료일자!!!
				
				Timestamp expiryTS = new Timestamp(expiry); // 만료일자 계산
				
				boolean isModifiedWithRememberMe = this.service.modifyUserWithRememberMe(vo.getUserid(), sessionId, expiryTS);
				log.info("\t+ 6. isModifiedWithRememberMe : {}", isModifiedWithRememberMe);
				
			} // if  						
		} else {         // 로그인 실패 - 다시 로그인 창으로 Redirection
			;;
		} // if-else
		
	} // postHandle
	
	 // 자동로그인 옵션이 on/off인지 체크하여 true or false 반환하는 메소드
	private boolean checkRememberMeOption(HttpServletRequest req) {
		log.trace("\t+ checkRememberMeOption(req) invoked.");
		
		String rememberMe = req.getParameter("rememberMe");
		
		return rememberMe != null;
	} // checkRememberMeOption

	
	// 3. Spring MVC의 View가 호출 완료된 직후에 콜백
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception e) throws Exception {
		log.trace("3. afterCompletion(req, res, {}, {}) invoked.", handler, e);
		
				
	} // afterCompletion

} // end class
