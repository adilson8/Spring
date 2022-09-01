package org.zerock.myapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.SampleVO;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/responseentity/")
@RestController
public class ResponseEntityRestController {

	// ResponseEntity<T> : 이 타입은 "응답메시지" 자체를 모델링 한 클래스입니다
	//                     이 타입은 "응답메시지"를 구성하는 3개 요소인
	//                     1) HTTP status code 2) Header 3) Body
	//                     를 모두 제어할 수 있는 타입 => 즉 응답메시지를 원하는대로 구성하는데 사용
	// 위의 타입파라미터 <T> : 응답메시지의 body에 넣을 자바객체의 타입을 지정
	// 결론적으로 응답메시지의 상태코드나 헤더를 제어할 수는 없지만 body는 제어 가능한
	// @ResponseBody와 상당히 비슷한 자바 클래스임
	
    // 전송파라미터로 어떤 사람의 키와 몸무게를 받고, 받은 인체수치를 체크하여 그 결과를
	// ResponseEntity 타입의 객체로 반환 => 결국 JSON or XML로 변환되어 응답메시지의 body에 넣어짐 
//	@GetMapping("/check")  // 기본값 : XML방식
	@GetMapping(
			path="/check", 
			params = {"height", "weight"},
			produces=MediaType.APPLICATION_JSON_VALUE)  // JSON 방식
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
	
		log.trace("check({}, {}) invoked.", height, weight);
		
		Ticket ticket = new Ticket(777, 8900., "B");
		SampleVO sampleVO = new SampleVO("동훈", 32, ticket);
		
		log.info("\t+ sampleVO : {}", sampleVO);
		
		ResponseEntity<SampleVO> response = null;
		BodyBuilder bodyBuilder = null;
		
		if(height < 20) { // 받은 키를 체크해보니 비정상적임
			bodyBuilder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
		} else {
			bodyBuilder = ResponseEntity.status(HttpStatus.OK);
		} // if-else
		
		log.info("\t+ bodyBuilder : {}, type : {}", bodyBuilder, bodyBuilder.getClass().getName());
		
		response = bodyBuilder.<SampleVO>body(sampleVO);
		log.info("\t+ response : {}, type : {}", response, response.getClass().getName());
		
		return response;
	
	} // check
	
} // end class
