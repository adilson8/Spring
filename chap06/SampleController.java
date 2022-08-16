package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


// 기본적으로 Spring Beans Container에 등록될 클래스는 자바빈즈 규약을 지키는 자바빈즈 클래스이다.
@Log4j2
@NoArgsConstructor

// Base URI 선언
@RequestMapping("/board/") // => 상세 URI : { "/doA","/doB", "/doC", "/iambabo" }

@Controller // 현재의 클래스의 역할 : Controller 정의
public class SampleController {
	
	// 컨트롤러에 선언되는 메소드 => 컨트롤러의 핸들러 메소드라고 부름
	// 무엇을 핸들링 하는가? => 특정 request를 처리한다는(handling) 의미
	// 1) 어떤 전송방식(HTTP Method)과  2) 어떤 Request URI를 가지고 들어온 Request를 처리하겠다라는 의미
	// 위의 2가지 정보를 설정하는 어노테이션이 @RequestMapping임
	
	@RequestMapping(
			path = { "/doA","/doB", "/doC", "/iambabo" }, // Base URI와 조합된 /board/doA가 최종 URI
			method = { RequestMethod.GET, RequestMethod.POST } // method 속성 생략시 모든 전송방식을 의미
			)
	public void doA() {
		log.trace("doA() invoked.");
	} // doA

} // end class
