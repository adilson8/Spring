package org.zerock.myapp.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@RequestMapping("/controller/")
// 이 컨트롤러는 RestController가 아니라 스프링 MVC 패턴을 따르는 컨트롤러임
// 따라서 핸들러 메소드가 문자열을 반환하면 이는 곧 뷰의 이름이 됨 => MVC에 의해 View(JSP) 호출
@Controller
public class ResponseBodyController {
	
	@ResponseBody
	@GetMapping("/responseBody")
	public String responseBody() {
		
		log.trace("responseBody() invoked.");
		
		return "home";		// 뷰의 이름 반환
	} // responseBody
	
	
	@ResponseBody
//	@GetMapping(path="/getTicket", produces=MediaType.APPLICATION_JSON_VALUE)
//	@GetMapping(path="/getTicket", produces=MediaType.APPLICATION_XML_VALUE)
	@GetMapping("/getTicket")  // XML과 같은 결과
	public Ticket getTicket() {
		log.trace("getTicket() invoked.");
		
		Ticket ticket = new Ticket();
		ticket.setTno(1234567890);
		ticket.setPrice(150000.);
		ticket.setGrade("C");
		
		return ticket;
	} // getTicket

} // end class
