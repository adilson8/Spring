package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/sample/")
@Controller
public class SampleController {
	
	@GetMapping("/doA")
	public void doA() {
		log.info("doA() invoked.");
		
		Integer.parseInt("백"); // 의도적으로 Runtime Exception을 발생시켜서 실험해보자
		
	} // doA
	
	
	@GetMapping("/doB")
	public void doB() {
		log.info("doB() invoked.");
		
	} // doB
	
	
	@GetMapping("/doC")
	public void doC() {
		log.info("doC() invoked.");
		
	} // doC
	
	
	@GetMapping("A/B/C/D/E/F/doD")
	public void doD() {
		log.info("doD() invoked.");
		
	} // doD

} // end class
