package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

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

import lombok.NoArgsConstructor;
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
public class HikariDataSourceTests {

	// 밑의 @Autowired 말고 이렇게도 할 수 있다.
	// @Resource(type=javax.sql.DataSource.class)
	// @Resource	
	// @Inject	

	// @Setter(onMethod_={ @Resource(type=javax.sql.DataSource.class) })
	// @Setter(onMethod_={ @Resource })
	// @Setter(onMethod_={ @Inject })
	// @Setter(onMethod_={ @Autowired })
	
	
	// 빈즈 컨테이너에 의존성 주입 시그널 전송
	@Autowired
	private DataSource dataSource;
	
	// 선처리 작업 : 필드에 원하는 타입의 빈 객체가 잘 주입되었는지 확인
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		// 필드에 의존성 객체가 주입되었는지 확인 (주입되었다면 null이 아님)
		assertNotNull(this.dataSource);				// 방법1
//		Objects.requireNonNull(this.dataSource);	// 방법2
//		assert this.dataSource != null;				// 방법3
		
		log.info("\t+ this.datasource : {}", this.dataSource);
		
	} // beforeAll
	
	@Test
	@Order(1)
	@DisplayName("1. javax.sql.DataSource.getConnection() method test.")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testGetConnection() throws SQLException {
		log.trace("testGetConnection() invoked.");
		
		// Connection Pool로부터 빌린 Connection
		Connection conn = this.dataSource.getConnection();
		
		// null인지 체크
		Objects.requireNonNull(conn);
		log.info("\t+ conn: {}, type: {}", conn, conn.getClass().getName());
		
		// Autoclosable한 자원객체 -> 썼으면 닫자 // @Cleanup, try-with-resources 추천
		conn.close(); 
		// 이 close는 Connection을 끊어버리지 않는다. Connection Pool에 반납하는 것

		// cf) jdbc에서 순서대로 자원객체 닫을 때 -> 거꾸로 적어준다
		// try(Connection; PreparedStatement; ResultSet;) { ;; }
		
	} // testGetConnection
	
	// 이 정도로는 부족하다! 실제 SQL문으로 확인해보자
	
	@Test
	@Order(2)
	@DisplayName("2. javax.sql.DataSource.getConnection() method test. with SQL test")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testGetConnectionWithSql() throws SQLException {
		log.trace("testGetConnectionWithSql() invoked.");
		
		// Connection Pool로부터 빌린 Connection
		Connection conn = this.dataSource.getConnection();
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
		
		try(conn; stmt; rs;) {
			
			while(rs.next()) {
				// rs.getXXX(컬럼명)에서 "컬럼명"은 대소문자를 구분하지 않음.
				String employee_id = rs.getString("employee_id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				String phone_number = rs.getString("phone_number");
				String hire_date = rs.getString("hire_date");
				String job_id = rs.getString("job_id");
				String salary = rs.getString("salary");
				String commission_pct = rs.getString("commission_pct");
				String department_id = rs.getString("department_id");
				
				log.info("{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, ",
						employee_id, first_name, last_name, email, phone_number, 
						hire_date, job_id, salary, commission_pct, department_id);				
			} // while			
		} // try-with-resources		
	} // testGetConnectionWithSql
	
	@Test
	void dummyTest() {
		log.trace("dummyTest() invoked.");	
	}

} // end class
