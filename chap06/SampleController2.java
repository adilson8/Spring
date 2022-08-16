package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


// 기본적으로 Spring Beans Container에 등록될 클래스는 자바빈즈 규약을 지키는 자바빈즈 클래스이다.
@Log4j2
@NoArgsConstructor

@RequestMapping("/board/") // Base URI

@Controller // 현재의 클래스의 역할 : Controller 정의
public class SampleController2 {
	
	
	// 1. @RequestMapping or @RequestMapping("")
	@RequestMapping("")      // Request URI == Base URI + Detail URI => "/board/*" + "" => /board/
//	@RequestMapping          // 상동
	public void basic() {
		log.trace("basic() invoked.");
		
		// Return 타입이 void일 때 반환되는 뷰의 이름은 Request URI
	} // basic
	
	
	// 2. @RequestMapping(path, method)
	@RequestMapping(
			path = { "/basicGet" },        // 값이 하나면 배열 기호인 중괄호 삭제 가능
			method = { RequestMethod.GET } // 값이 하나면 배열 기호인 중괄호 삭제 가능
			)
	public void basicGet() {
		log.trace("basicGet() invoked.");
		
		// Return 타입이 void일 때 반환되는 뷰의 이름은 Request URI
	} // basicGet
	
	
	// 3. @RequestMapping(path, method)
	@RequestMapping(
			path = "/basicGetPost",
			method = { RequestMethod.GET, RequestMethod.POST }) // Get방식, Post방식 둘다 가능!
	public void basicGetPost() {
		log.trace("basicGetPost() invoked.");
	} // basicGetPost
	
	
	// 4. @GetMapping(path)
//	@RequestMapping (path = "/basicOnlyGet", method=RequestMethod.GET )
	@GetMapping("/basicOnlyGet") // 이 어노테이션은 위와 동일함
	public void basicOnlyGet() {
		log.trace("basicOnlyGet() invoked.");
	} // basicOnlyGet
	
	
	// 5. @PostMapping(path)
//	@RequestMapping (path = "/basicOnlyPost", method=RequestMethod.POST )
	@PostMapping("/basicOnlyPost") // 이 어노테이션은 위와 동일함
	public void basicOnlyPost() {
		log.trace("basicOnlyPost() invoked.");
	} // basicOnlyPost
	

	// 6. @GetMapping(path) with DTO parameter
	
	// 컨트롤러의 핸들러는 아래의 역할 및 값을 반환 : 
	// 1) 역할 : 요청을 위임받아 처리
	// 2) 뷰의 이름을 봔환 
	// 3) 역할에 따라 요청을 위임처리한 결과 Model(비지니스 데이터)을 생성 및 View로 전송
	@GetMapping("/ex01")
	public String ex01() {
		log.trace("ex01() invoked.");

		// servlet-context.xml에서 설정한 View Resolver Configuration에 의해
		// Prefix + ViewName + Suffix = /WEB-INF/views/ + ex01 + .jsp = /WEB-INF/views/ex01.jsp
		return "ex01"; // MVC 패턴에서 View의 이름
	} // ex01
	
	
	
} // end class
