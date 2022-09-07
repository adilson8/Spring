package org.zerock.myapp.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

// To register manually this interceptor as a bean in the srevlet-context.xml
//@Component     // Auto-registration as a bean
public class SampleInterceptor implements HandlerInterceptor{

	
	// 1. HTTP Request가 Controller의 Handler method로 위임되기 직전에 콜백
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("1. preHandle(req, res, {}) invoked.", handler);
		
		log.info("\t+ 1. handler : {}", handler);
		log.info("\t+ 2. type : {}", handler.getClass().getName());
		
		HandlerMethod requestHandler = (HandlerMethod) handler;
		Object controller = requestHandler.getBean();
		Method method = requestHandler.getMethod();
		
		log.info("\t+ 3. requestHandler : {}", requestHandler);
		log.info("\t+ 4. controller : {}", controller);
		log.info("\t+ 5. method : {}", method);
		
		// return 타입 boolean에 대해 이해해보자
		// if return false, incoming request를 뒤로 넘기지 않음 (체인이 있던 없던)
		// if return true, incoming request를 뒤로 넘김 (체인이 있던 없던)
		
		// 예제 : 요청을 보낸 클라이언트의 IP주소를 체크하여 위험한 소스에서 온 요청이면
		//        그 즉시 요청 처리를 끝내버림 (through returning false)
//		String clientAddr = req.getRemoteAddr();
//		log.info("\t+ 6. clientAddr : {}", clientAddr);
//		return ("192.168.110.19".equals(clientAddr) ? false : true); // 내가 보낸 요청이면 넘기면 안 됨

		return true;
	} // preHandle
	
	
	// 2. Controller의 Handler method가 수행 완료된 직후에 콜백
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,	ModelAndView modelAndView) throws Exception {
		log.trace("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		
		log.info("\t+ 1. modelAndView : {}", modelAndView);
		
		ModelMap model = modelAndView.getModelMap();	// Model 얻기
		String viewName = modelAndView.getViewName();	// View 이름 얻기
		HttpStatus status = modelAndView.getStatus();	// 응답 상태코드 얻기
		
		log.info("\t+ 2. model : {}", model);			// Model 찍어보기
		log.info("\t+ 3. viewName : {}", viewName); 	// View 이름 찍어보기
		log.info("\t+ 4. status : {}", status);			// 응답 상태코드 찍어보기
		
		modelAndView.setViewName("redirect:/login");	// 필요하면 특수한경웨 뷰 이름을 변경하여 다른 화면으로 이동시킴
		modelAndView.setStatus(HttpStatus.OK);			// 필요하면 응답 상태 코드까지 변경하여 브라우저로 전송
		model.put("serverTime", "^^;;");				// 필요하면 모델에 추가 데이터를 넣어서 뷰에 전달 가능
		
		log.info("\t+ 5. modelAndView : {}", modelAndView);
		
	} // postHandle

	
	// 3. Spring MVC의 View가 호출 완료된 직후에 콜백
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception e) throws Exception {
		log.trace("3. afterCompletion(req, res, {}, {}) invoked.", handler, e);
		
		log.info("\t+ 1. e : {}", e);
				
	} // afterCompletion

} // end class
