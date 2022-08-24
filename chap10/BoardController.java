package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.exception.ControllerException;
import org.zerock.myapp.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2

// Spring 4.3 이후 부터는 주입받을 필드가 1개이고 이 필드를 매개변수로 가지는 생성자를 만들면
// 주입 시그널을 보내지 않아도 자동으로 주입해준다.
@AllArgsConstructor

@RequestMapping("/board/") // Base URI
@Controller
public class BoardController  {
	
	// Spring 4.3 이후 부터는 주입받을 필드가 1개이고 이 필드를 매개변수로 가지는 생성자를 만들면
	// 주입 시그널을 보내지 않아도 자동으로 주입해준다.
	private BoardService service;
	
	@GetMapping("/list") // 상세 URI
	public void list(Model model) throws ControllerException {
		log.trace("list() invoked.");
		
		try {
			List<BoardVO> list = this.service.getList();
			
			model.addAttribute("list",list);

			// View Name = Base URI + 상세 URI			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

	} // list
	
	
	
	
} // end class
