package org.zerock.myapp.controller;

import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.myapp.domain.ParamsDTO;
import org.zerock.myapp.domain.SampleDTO;

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
		
		// Return 타입이 void일 때 반환되는 뷰의 이름은 Request URI => "/board/"
	} // basic
	
	
	// 2. @RequestMapping(path, method)
	@RequestMapping(
			path = { "/basicGet" },        // 값이 하나면 배열 기호인 중괄호 삭제 가능
			method = { RequestMethod.GET } // 값이 하나면 배열 기호인 중괄호 삭제 가능
			)
	public void basicGet() {
		log.trace("basicGet() invoked.");
		
		// Return 타입이 void일 때 반환되는 뷰의 이름은 Request URI => "/board/basicGet"
	} // basicGet
	
	
	// 3. @RequestMapping(path, method)
	@RequestMapping(
			path = "/basicGetPost",
			method = { RequestMethod.GET, RequestMethod.POST }) // Get방식, Post방식 둘다 가능!
	public void basicGetPost() {
		log.trace("basicGetPost() invoked.");
		
		// Return 타입이 void일 때 반환되는 뷰의 이름은 Request URI => "/board/basicGetPost"
	} // basicGetPost
	
	
	// 4. @GetMapping(path)
//	@RequestMapping (path = "/basicOnlyGet", method=RequestMethod.GET )
	@GetMapping("/basicOnlyGet") // 이 어노테이션은 위와 동일함
	public void basicOnlyGet() {
		log.trace("basicOnlyGet() invoked.");
		
		// Return 타입이 void일 때 반환되는 뷰의 이름은 Request URI => "/board/basicOnlyGet"
		// View Resolver가 생성한 실제 뷰의 경로 
		// => Prefix + viewName + Suffix = "/WEB-INF/views/board/basicOnlyGet.jsp"
	} // basicOnlyGet
	
	
	// 5. @PostMapping(path)
//	@RequestMapping (path = "/basicOnlyPost", method=RequestMethod.POST )
	@PostMapping("/basicOnlyPost") // 이 어노테이션은 위와 동일함
	public void basicOnlyPost() {
		log.trace("basicOnlyPost() invoked.");
		
		// Return 타입이 void일 때 반환되는 뷰의 이름은 Request URI => "/board/basicOnlyPost"
	} // basicOnlyPost
	

	// 6. @GetMapping(path) with Request Parameters
	
	// 컨트롤러의 핸들러는 아래의 역할 및 값을 반환 : 
	// 1) 역할 : 요청을 위임받아 처리
	// 2) 뷰의 이름을 봔환 
	// 3) 역할에 따라 요청을 위임처리한 결과 Model(비지니스 데이터)을 생성 및 View로 전송
//	@GetMapping("/ex01")
//	public String ex01 (String name, int age) { // DispatcherServlet에게 2개 전송파라미터를 이 매개변수에 넣어달라고 부탁하는 것
//		log.trace("ex01({}, {}) invoked.", name, age);
//
//		// servlet-context.xml에서 설정한 View Resolver Configuration에 의해
//		// Prefix + ViewName + Suffix = /WEB-INF/views/ + ex01 + .jsp = /WEB-INF/views/ex01.jsp
//		return "ex01"; // MVC 패턴에서 View의 이름
//	} // ex01
	
	@GetMapping("/ex01")
	public String ex01 (ParamsDTO dto) {
		log.trace("ex01({}, {}) invoked.", dto);

		// servlet-context.xml에서 설정한 View Resolver Configuration에 의해
		// Prefix + ViewName + Suffix = /WEB-INF/views/ + ex01 + .jsp = /WEB-INF/views/ex01.jsp
		return "ex01"; // MVC 패턴에서 View의 이름
	} // ex01
	
	
	// 7. @GetMapping(path) with some primitive type's parameters
	
	// 컨트롤러의 핸들러 메소드의 매개변수로 기본타입을 사용하면 안 됨
	// 기본타입의 매개변수가 필요할 시에는 기본타입에 대응되는 wrapper type 사용할 것
	// 전송파라미터가 오지 않았을 때 null로 표현해주어야하기 때문 (기본 타입은 null 표현 못하잖슴)
	// 더불어 우리가 지정하는 매개변수의 이름은 기본적으로 전송 파라미터의 이름과 동일해야함
	// 그래야 DispatcherServlet이 이름에 맞는 전송파라미터의 값을 자동으로 넣어줌
	@GetMapping("/ex02")
	public String ex02( // @RequestParam("") 을 이용해 다른 전송 파라미터로 와도 잡아주자
			@RequestParam("name") String recvName,  // name이라는 파라미터로 와도 recvName로 받을 수 있다.
			@RequestParam("age") Integer currentAge // 기본타입 int가 아니고 wrapper 타입 Integer로 넣어준다
			) { 
		log.trace("ex02({}, {}) invoked.", recvName, currentAge );

		return "ex02"; // 반환된 뷰의 이름
	} // ex02
	
	
	// 8. @GetMapping(path) with multi-values request parameters
	
	@GetMapping("/ex03")
//	public String ex03( @RequestParam("hobby") List<String> hobby) { // 1번째 방법
//	public String ex03( @RequestParam("hobby") ArrayList<String> hobby) { // 2번째 방법
	public String ex03(String[] hobby) { // 3번째 방법 (가장 확실한 방법)
		log.trace("ex03({}) invoked.", Arrays.toString(hobby)); // 3번째 방법
		
//		log.trace("ex03({}) invoked.", hobby); // 1, 2번째 방법

		return "ex03";
	} // ex03
	
	
	// 9. @DateTimeFormat

	// 전송 파라미터의 값이 날짜 포맷 형식으로 전달될 때, 
	// 컨트롤러의 핸들러 메소드의 매개변수로 아예 처음부터 Date 객체로 얻을 수 있다.
	@GetMapping("/ex04")
	public String ex04( @DateTimeFormat(pattern = "yyyy-MM-dd") Date hireDate) {
		log.trace("ex04({}) invoked.", hireDate);
		
		return "ex04";
	} // ex04
	
	
	// 10. Predefined Model parameter
	
	@GetMapping("/ex05")
	public String ex05(
			// 1. 각 전송 파라미터의 값 획득 from DispatcherServlet
//			String name, int age, 
			SampleDTO dto, int page, 
			// 2. 핵심 비지니스 로직의 수행 결과데이터를 저장 및 View에 전달하는 상자
			Model model
			) {
		log.trace("ex05({}, {}, {}) invoked.", dto, page, model);
		
		log.info("\t+ model type : {}", model.getClass().getName());
		
		// 3. Model 상자에 저장할 객체 생성
//		SampleDTO dto = new SampleDTO();
//		dto.setName(name);
//		dto.setAge(age);
		
		// 4. 비지니스 데이터를 저장하는 "상자" 역할을 하는 Model 객체 
//		model.addAttribute("sampleDTO", dto);
		model.addAttribute("page", page);
		
		return "commandObject"; // 반환된 뷰의 이름
	} // ex05
	
	
	// 11. @ModelAttribute(key) to transfer data into the View
	
	@PostMapping("/ex06")
//	public String ex06 (SampleDTO dto, Integer page) { // 이렇게 하면 안 된다 (이렇게 하면 위 10번의 4. 처럼 model.addAttribute 해줘야함)
	public String ex06 (SampleDTO dto, @ModelAttribute("page") Integer page ) { // 이렇게 해보자
		
		log.trace("ex06({}, {}) invoked.", dto, page);
		
		return "commandObject"; // 반환된 뷰의 이름		
	} // ex06
	
	
	// 12. Predefined RedirectAttributes
	@GetMapping("/ex07")
	public String ex07 (
			String name, Integer age,
			// rttrs는 Model 상자와 비슷한 역할 (임시상자역할)
			// 리다이렉션의 Target URL에 전달할 전송파라미터를 만들어내는 역할
			RedirectAttributes rttrs
			) {
		log.trace("ex07({}, {}, {}) invoked.", name, age, rttrs);
		
		// 1. 1회성 데이터 전달 => Request Scope에 공유속성으로 올려서 전달(공유)
//		rttrs.addFlashAttribute("name", name);
//		rttrs.addFlashAttribute("age", age);
		
		// 2. 전송파라미터로 전달 => GET 방식의 Query String 형태로 전달 (이걸로 하자 (강사님추천))
		rttrs.addAttribute("name", name);
		rttrs.addAttribute("age", age);

		// redirect: 특수문자열 뒤에는 재요청을 보낼 Target URL 지정
		//           이 반환된 문자열은 View의 이름이 아님 (View가 호출되지 않음)
		//           바로 307 Redirect 응답문서가 즉시 전송됨.
		return "redirect:/board/ex02"; 

	} // ex07	
	
} // end class
