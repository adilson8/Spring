package org.zerock.myapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// Spring Beans Container & DI 수행시키는 어노테이션
// for JUnit5
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")

// Spring MVC Framework 구동시키는 어노테이션 (이제 얘도 필수로 붙여주자)
@WebAppConfiguration

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)


public class BoardControllerTests {


	// DI 수행 (주입)
	// @Setter(onMethod_= @Autowired )
	// private BoardController controller;
	// WAS를 매번 올렸다 내렸다 테스트 하기 너무 귀찮다 이렇게 하지 말고 다른 방식을 배워보자
	
	// Spring Beans Container (type: WebApplicationContext) 자체를 주입
	@Setter(onMethod_= @Autowired )
	private WebApplicationContext ctx;
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		// null 인지 체크
		assertNotNull(this.ctx);
		
		log.info("\t+ ctx : {}", this.ctx);
	} // beforeAll
	
	
	@Test
	@Order(1)
	@DisplayName("1. testList")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	public void testList() throws Exception {
		log.trace("testList() invoked.");
		
		// 1. WAS 구동없이 Controller의 Handler Method를 테스트하려면 spring-test 의존성에 포함된
		// MockMvc 객체가 필요함. MockMvc 객체를 생성하기 위해서 위에서 Spring Beans Container를 주입한 것!
		
		// 2. 아래의 MockMvc의 메소드들은 fluent-api, method-chaining 기법으로 사용하게 됨
		
		// Step 1. MockMvcBuilder 객체 얻기 
		MockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(ctx);
		
		// Step 2. MockMvc 객체 생성하기
		MockMvc mockMvc = mockMvcBuilder.build();
		
		// Step 3. RequestBuilder 객체 생성하기
		// (1) 전송 파라미터가 없는 경우에는 아래와 같이 하고
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/board/list");
		
		// (2) 전송 파라미터까지 보내야 하는 경우에는, 아래와 같이 함
//		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/board/list");
//		reqBuilder.param("전송 파라미터 이름", "전송 값");
//		reqBuilder.param("name", "John");
//		reqBuilder.param("age", "23");
//		reqBuilder.param("hobby", "Book", "Movie", "Music");
		
		// Step 4. 실제 Controller로 요청(Request) 보내기
		//         요청을 보낸 결과로 Model & View 이름을 받을 수 있음
		ResultActions resultActions =  mockMvc.perform(reqBuilder);
		
		// Step 5. Step 4에서 발생한 Model & View 이름을 얻어냄
		resultActions.andReturn();
		
	} // testList
	
	
	
} // end class
