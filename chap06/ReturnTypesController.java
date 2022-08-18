package org.zerock.myapp.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.myapp.domain.SampleDTO;

import com.google.gson.Gson;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/return/")

@Controller
public class ReturnTypesController {

	// 1. void return type
	
	@GetMapping("/void")
	public void returnVoid() {
		log.trace("returnVoid() invoked.");
		
		// 뷰 이름 -> Request URI (Base URI + Detail URI)임
	} // returnVoid
	
	// 2. String return type
	
	@GetMapping("/String")
	public String returnString() {
		log.trace("returnString() invoked.");
		
		return "return/string"; // /WEB-INF/views + return/string + .jsp
	} // returnString
	
	// 2-1. String return type : "redirect:" keyword
	@GetMapping("/redirect")
	public String returnRedirect() {
		log.trace("returnRedirect() invoked.");
		
		// 307 리다이렉션 응답전송을 의미 (뷰가 호출되는게 아니고 응답이 나옴)
		return "redirect:/return/void";
//		return "redirect:/void";                  // OK : Base URI를 생략해도 문제없음
//		return "redirect:http://naver.com";		  // OK : 외부 URL로 리다이렉트 수행
	} // returnRedirect
	
	// 2-2. String return type : "forward:" keyword
	@GetMapping("/forward")
	public String returnForward() {
		log.trace("returnForward() invoked.");

		// Servlet에서 우리가 직접 Request Forwarding 수행시 코드는 아래와 같습니다
		// 잘 기억해보세요
//		ReqeustDispatcher dispatcher = request.getRequestDispatcher(target_url);
//		dispatcher.forward(request, response);		
		
//		return "forward:/WEB-INF/views/return/void.jsp";
//		return "forward:/return/void";					 // OK : 어떤 것이든 우리가 만든 컨트롤러 핸들러로 요청을 떠 넘기는 경우
//		return "forward:/void";							 // OK : Base URI를 생략해도 문제없음
//		return "forward:/http://naver.com";		 		 // XX : 리다이렉트와 달리 외부 URL은 허용하지 않음
		return "forward:/board/basicGet";  				 // OK : 같은 웹어플리케이션 내의 다른 컨트롤러 핸들러에 (SampleControler2 참고)
	} // returnForward
	
	
	// 3. Object return type using @ResponseBody
	@GetMapping("/ResponseBody")
	public @ResponseBody SampleDTO returnResponseBody(
			@NonNull @RequestParam("name") String NAME,
			@NonNull @RequestParam("age") Integer AGE
			) {
		log.trace("returnResponseBody({}, {}) invoked.", NAME, AGE);
		
		SampleDTO dto = new SampleDTO();
		dto.setName(NAME);
		dto.setAge(AGE);
		
		log.info("\t+ dto : " + dto);
		
		// 반환할 자바객체를 표현가능한 문서형식 (JSON/XML)으로 변환해서 응답메시지의 바디에 넣어서 전달해줘야한다.
		// 그래야 409 오류 (Not Acceptable)가 발생하지 않습니다.
		// 하지만 이를 위해서는 소위 변화 라이브러리(==Data-binding libraries)가 필요합니다.
		// 스프링에서 가장 많이 사용하는 Data-binding 라이브러리로 Jackson 계열의 라이브러리를 사용합니다.
		// 이 라이브러리를 pom.xml에 추가해 줍니다. (자바객체 -> JSON/XML로 변환)
		return dto; 	// 일반 참조타입의 자바객체를 반환하는 경우
	} // returnResponseBody
	
	// 4. Return type ResponseEntity
	
	@GetMapping("/ResponseEntity")
	public ResponseEntity<String> returnResponseEntity() {
		log.trace("returnResponseEntity() invoked.");
		
//		String json = "{'name': 'Yoseph', 'age': 23 }";
		
		// 위에는 하드코딩, 우리는 객체를 만들어서 해보자
//		SampleDTO dto = new SampleDTO();
//		dto.setName("John");
//		dto.setAge(32);
		
		Person person = new Person(); // Person 클래스는 맨 밑을 확인하세요
		
		Gson gson = new Gson();
		String json = gson.toJson(person);
		log.info("\t+ json : {}", json);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf8");

		return new ResponseEntity<>(json, headers, HttpStatus.OK);
	} // returnResponseEntity

	
} // end class

class Person { 
	private String name = "John";
	private Integer age = 32;
	private Double height = 180.3;
	private Double weight = 83.5;
	private String[] hobby = { "운동", "영화", "음악", "게임" };
	
	public String sayHello() {
		return "Hello";
	} // sayHello
	
} // Person


