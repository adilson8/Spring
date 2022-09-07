package org.zerock.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Aspect    // Aspect를 구현한 것을 나타내기 위해 사용 (component scan을 통해 자동으로 빈으로 등록불가)
@Component // 스프링 빈으로 등록하기 위한 어노테이션 (AOP와는 무관)
public class LogAdvice {  // POJO : Plain Old Java Object

	
	// POINTCUT EXPRESSION 생성은, AsepectJ 언어에서 제공하는 함수를 이용
	// 이 pointcut 설정함수 이름은 "execution()"
	// 이 execution 함수를 호출시, pointcut 설정내역을 인자값으로 매개변수에 전달해야함  
	@Before( "execution( * org.zerock.myapp.service.*Service.*(..) )" )  
	// Service로 끝나는 모든 클래스의 모든 메서드의 모든 매개변수(..)에 적용
	public void logBefore() {
		log.trace("==============================");
		log.trace("logBefore() invoked.");
		log.trace("==============================");
		
	} // logBefore
	
} // end class
