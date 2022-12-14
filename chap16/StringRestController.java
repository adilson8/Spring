package org.zerock.myapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/RESTful/")
@RestController
public class StringRestController {
	
	@ResponseBody // 일반적인 JSP와 같은 뷰로 전달되는 게 아니라 데이터 자체를 전달하기 위한 용도 
	@GetMapping(path="/getText", produces= MediaType.TEXT_PLAIN_VALUE + "; charset=utf8")
	public String getText() {
		log.trace("getText() invoked.");
		
		// @RestController의 핸들러 메소드에서 반환하는 문자열은 더이상 뷰의 이름이 아닌
		// 실제 순수 데이터로서, 텍스트 문자열을 반환한다는 의미임.		
		return "안녕하세요 저의 이름은 John 입니다.";
	} // getText

} // end class
