package org.zerock.myapp.service;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
// 이 서비스 객체가 바로 AOP에서 말하는 Target 객체를 의미함!!!
// Target 객체 : 핵심 비지니스 로직을 메소드로 구현해 놓은 객체
public class SampleServiceImpl implements SampleService {

	@Override
	// 이 메소드가 AOP에서 말하는 JointPoint 메소드임!!!
	public Integer doAdd(String op1, String op2) throws Exception {
		log.info("doAdd({}, {}) invoked.", op1, op2);
		
		return Integer.parseInt(op1) + Integer.parseInt(op2);
	} // doAdd

} // end class
