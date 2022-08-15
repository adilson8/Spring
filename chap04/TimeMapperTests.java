package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
import org.zerock.myapp.mapper.TimeMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

//테스트 메소드 수행시 스프링 프레임워크까지 함께 구동되도록 해주는 어노테이션 설정 추가
// JUnit5 방식
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeMapperTests {
	
	@Setter(onMethod_= {@Autowired})
	private SqlSessionFactory sqlSessionFactory;
	
	@BeforeAll
	void boforeAll() {
		log.trace("boforeAll() invoked.");
		
		// 필드에 의존성 객체가 주입되었는지 확인 (주입되었다면 null이 아님)
		assertNotNull(this.sqlSessionFactory);				// 방법1
//		Objects.requireNonNull(this.sqlSessionFactory);	    // 방법2
//		assert this.sqlSessionFactory != null;				// 방법3
		
		log.info("\t+ this.sqlSessionFactory : {}", this.sqlSessionFactory);
		
	} // boforeAll
	
//	@Disabled
	@Test
	@Order(1)
	@DisplayName("1. testGetCurrentTime1")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testGetCurrentTime1() {
		log.trace("testGetCurrentTime1() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {

			// Mapper Interface 방식은 이렇게 한다!
			TimeMapper mapper = sqlSession.getMapper(TimeMapper.class);
			
			assertNotNull(this.sqlSessionFactory);
			log.info("\t+ mapper : {}, type : {}", mapper, mapper.getClass().getName() );
			
			String time = mapper.getCurrentTime1();
			log.info("\t+ time : {}", time);
			
		} // try-with-resources
	
	} // testGetCurrentTime1
	
//	@Disabled
	@Test
	@Order(2)
	@DisplayName("2. testGetCurrentTime2")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testGetCurrentTime2() {
		log.trace("testGetCurrentTime2() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {

			// Mapper Interface 방식은 이렇게 한다!
			TimeMapper mapper = sqlSession.getMapper(TimeMapper.class);
			
			assertNotNull(this.sqlSessionFactory);
			log.info("\t+ mapper : {}, type : {}", mapper, mapper.getClass().getName() );
			
			String time = mapper.getCurrentTime2();
			log.info("\t+ time : {}", time);
			
		} // try-with-resources
	
	} // testGetCurrentTime2
	
} // end class
