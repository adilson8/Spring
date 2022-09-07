package org.zerock.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ControllerAdvice
public class CommonExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler ( Exception.class)
	public String handleException(Exception e, Model model) { // 모든 예외의 처리 핸들러
		log.trace("handleException() invoked.", e, model);
		
		model.addAttribute("_EXCEPTION_", e);
		
		return "errorPage"; // 뷰의 이름 반환		
	} // handleException
	
} // end class
