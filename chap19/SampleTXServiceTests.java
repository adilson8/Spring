package org.zerock.myapp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

//JUnit5 방식
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SampleTXServiceTests {

	// 주입
	@Setter(onMethod_=@Autowired)
	private SampleTXService service;

	
	// 주입이 되었는지 1회성 전처리 과정을 통해 확인하자
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
			
		// 주입이 되었는지 확인 (주입이 되었다면 null이 아님)
		assertNotNull(this.service);
		log.info("\t+ this.service : {}", this.service);
		log.info("\t+ type : {}", this.service.getClass().getName());
		
	} // beforeAll
	
	
	@Test
	@Order(1)
	@DisplayName("1. test계좌이체")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void test계좌이체() throws Exception  {
		log.trace("test계좌이체() invoked.");
		
		// 0. 이체금액 정의
		String transferMoney = "123456789012345678901234567890123456789012345678901"; // length : 51;
		
		this.service.계좌이체(transferMoney);
		log.info("계좌이체가 잘 되었습니다.");
		
	} // test계좌이체
	

} // end class
