package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Objects;
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
import org.zerock.myapp.domain.EmployeeVO;
import org.zerock.myapp.mapper.EmployeesMapper;

import lombok.Cleanup;
import lombok.Data;
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
public class SqlSessionFactoryTests {
	
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
	@DisplayName("1. testFirstMapperXML")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testFirstMapperXML() {
		log.trace("testFirstMapperXML() invoked.");
		
		@Cleanup
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		// Mapped Statement를 결정하기 위한 요소 2가지
		String namespace = "FirstMapper";
		String sqlId = "DQL1";		
		String sql = namespace + "." + sqlId;
		
//		sqlSession.selectList(sql, 130);
		List<EmployeeVO> list = sqlSession.<EmployeeVO>selectList(sql, 130);
		list.forEach(log::info);
	
	} // testFirstMapperXML
	
//	@Disabled
	@Test
	@Order(2)
	@DisplayName("2. testSecondMapperXML")
	@Timeout(value = 20, unit = TimeUnit.SECONDS)
	void testSecondMapperXML() {
		log.trace("testSecondMapperXML() invoked.");
		
		@Cleanup
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		// Mapped Statement를 결정하기 위한 요소 2가지
		String namespace = "SecondMapper";
		String sqlId = "DQL2";		
		String sql = namespace + "." + sqlId;
		
		// SQL 문장에 줄 바인딩 변수가 2개! 이러면 Map객체 혹은 자바빈즈 객체를 사용한다.
		// 지난번엔 Map 객체로 했다! (90일차) 이번엔 자바빈즈로 해보자 (property가 바인딩 변수명)
		
		@Data
		class Parameters { // 로컬 클래스로 자바빈즈 클래스를 만들자
			private String email;
			private Double sal;
		} // end class
		
		// 자바빈즈 객체를 생성하자
		Parameters params = new Parameters();
		
		// 바인딩 변수의 값을 넣어주자
		params.setEmail("%A%");
		params.setSal(7000.0);
		
		// 자바빈즈 객체 params로 전해주자
		List<EmployeeVO> list = sqlSession.<EmployeeVO>selectList(sql, params);
		list.forEach(log::info);		
		
	} // testSecondMapperXML
	
//	@Disabled
	@Test
	@Order(3)
	@DisplayName("3. testGetAllEmployeesInEmployeesMapper")
	@Timeout(value = 20, unit = TimeUnit.SECONDS)
	void testGetAllEmployeesInEmployeesMapper() {
		log.trace("testGetAllEmployeesInEmployeesMapper() invoked.");
		
		@Cleanup
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			
			// Mapper Interface 방식은 이렇게 한다!
			EmployeesMapper mapper = sqlSession.<EmployeesMapper>getMapper(EmployeesMapper.class);
			
			Objects.requireNonNull(mapper);
			log.info("\t+ mapper : {}, type : {}", mapper, mapper.getClass().getName() );
			
			mapper.getAllEmployees().forEach(log::info);
			
		} // try-with-resources
		
	} // testGetAllEmployeesInEmployeesMapper
	
//	@Disabled
	@Test
	@Order(4)
	@DisplayName("4. testGetAllEmployeesNames")
	@Timeout(value = 20, unit = TimeUnit.SECONDS)
	void testGetAllEmployeesNames() {
		log.trace("testGetAllEmployeesNames() invoked.");
	
		@Cleanup
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {

			// Mapper Interface 방식은 이렇게 한다!
			EmployeesMapper mapper = sqlSession.getMapper(EmployeesMapper.class);
			
			assertNotNull(mapper);
			log.info("\t+ mapper : {}, type : {}", mapper, mapper.getClass().getName() );
			
			mapper.getAllEmployeesNames().forEach(log::info);
			
		} // try-with-resources
		
	} // testGetAllEmployeesNames

} // end class
