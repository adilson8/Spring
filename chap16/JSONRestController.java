package org.zerock.myapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.SampleVO;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/RESTful")
@RestController
public class JSONRestController {
	
//	@GetMapping(path="/getSampleVO.json", produces=MediaType.APPLICATION_XML_VALUE)   // XML format으로 변환
	@GetMapping(path="/getSampleVO.json", produces=MediaType.APPLICATION_JSON_VALUE)  // JSON format으로 변환
	public SampleVO getSampleVO_json() {		
		
		log.trace("getSampleVO_json() invoked.");
		
		SampleVO vo = new SampleVO("진동훈", 32, null);
		
		// @RestController에서는 반환하는 것이 자바객체라면 스프링이 이 반환하는 자바 객체를
		// 의도한 대로 XML or JSON으로 자동변환 시켜서 응답문서의 바디에 넣어준다.
		return vo;
	} // getSampleVO_json 
	
	
	@GetMapping(path="/getTicket.json", produces=MediaType.APPLICATION_JSON_VALUE)  // JSON format으로 변환
	public Ticket getTicket() {		
		
		log.trace("getTicket() invoked.");
		
		Ticket ticket = new Ticket();
		ticket.setTno(1234567890);
		ticket.setPrice(150000.);
		ticket.setGrade("C");
		
		// @RestController에서는 반환하는 것이 자바객체라면 스프링이 이 반환하는 자바 객체를
		// 의도한 대로 XML or JSON으로 자동변환 시켜서 응답문서의 바디에 넣어준다.
		return ticket;
	} // getTicket 
	
	
	@GetMapping(path="/getTickets.json", produces=MediaType.APPLICATION_JSON_VALUE)  // JSON format으로 변환
	public List<Ticket> getTickets() {
		log.trace("getTickets() invoked.");
		
		List<Ticket> list = new ArrayList<>();
				
		for(int i=1; i<=5; i++) {
			Ticket ticket = new Ticket(i, 1000.*i, ( (i < 3)? "C" : ( (i==4)? "B" : "A") ) );
			list.add(ticket);
		} // for
		
		return list;
	} // getTickets
	
	
	@ResponseBody // 일반적인 JSP와 같은 뷰로 전달되는 게 아니라 데이터 자체를 전달하기 위한 용도 
	@GetMapping(path="/getSpecialSampleVO.json", produces=MediaType.APPLICATION_JSON_VALUE)  // JSON format으로 변환
	public SampleVO getSpecialSampleVO() {
		log.trace("getSpecialSampleVO() invoked.");
		
		SampleVO vo = new SampleVO("동훈", 32, new Ticket(1000, 250., "D"));
		log.info("\t+ vo : {}", vo);
		
		return vo;
	} // getSpecialSampleVO

} // end class
