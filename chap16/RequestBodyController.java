package org.zerock.myapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/requestbody/")
@RestController
public class RequestBodyController {
	
	
	// RequestBody : 요청메시지의 바디에 들어있는 데이터를 추출하여 매개변수에 저장하라!
	@GetMapping(path="/getTicket", produces=MediaType.APPLICATION_XML_VALUE)
	public Ticket getTicket( @RequestBody Ticket ticket) {
		log.trace("getTicket({}) invoked.", ticket);
		
		return ticket;
	} // getTicket

} // end class
