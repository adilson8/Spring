package org.zerock.myapp.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.myapp.domain.Chef;
import org.zerock.myapp.domain.Hotel;
import org.zerock.myapp.domain.Restaurant;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// 테스트 메소드 수행시 스프링 프레임워크까지 함께 구동되도록 해주는 어노테이션 설정 추가

// For JUnit v4.x
//@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)

// For JUnit v5.x
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations= {
//	필요한 스프링 설정파일을 등록해줌. 이때 file: 이 사용되는데
//	이 file: 의 의미는 프로젝트 폴더와 같음
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DITests {
	
	// 이 JUnit 테스트 클래스도, JUnit test framework에 의해서 구동시 기본생성자를 이용하여 객체를 생성하고
	// 이 객체는 자동으로 Spring Beans Container의 Bean으로 등록됨

//	@Resource(type=Chef.class)
//	@Resource	
//	@Inject	
//	@Autowired
	
	@Autowired						// 의존성 주입 시그널 정송 to Beans Container
	private Restaurant restaurant;
	
	@Autowired
	private Hotel hotel;
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		assertNotNull(this.restaurant);				// 방법1
//		Objects.requireNonNull(this.restaurant);	// 방법2
//		assert this.restaurant != null;				// 방법3
		
		log.info("\t+ 1. this.restaurant: {}", restaurant);
		
//		---
		
		assertNotNull(this.hotel);				// 방법1
//		Objects.requireNonNull(this.hotel);		// 방법2
//		assert this.hotel != null;				// 방법3
		
		log.info("\t+ 2. this.hotel: {}", hotel);
		
	} // beforeAll
	
//	@BeforeEach
//	
//	@Test
//	
//	@AfterEach
//	
//	@AfterAll
	
	@Test
	void dummyTest() {
		;;
	}

} // end class
