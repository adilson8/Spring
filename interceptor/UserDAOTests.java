package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.util.Objects;
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
import org.zerock.myapp.domain.LoginDTO;
import org.zerock.myapp.domain.UserVO;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTests {

	// 주입
	@Setter(onMethod_=@Autowired)
	private UserDAO dao;

	
	// 주입이 잘 되었는지 1회성 전처리로 확인 
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		// null 인지 체크
		assertNotNull(this.dao);
		
		log.info("\t+ dao : {}", this.dao);
	} // beforeAll
	

	
	@Test
	@Order(1)
	@DisplayName("1. testSelectUser")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testSelectUser() throws Exception {
		log.info("testSelectUser() invoked.");

		LoginDTO dto = new LoginDTO();
		dto.setUserid("USER_3");
		dto.setUserpw("PASS_3");
		
		UserVO vo = this.dao.selectUser(dto);
		Objects.requireNonNull(vo);
		log.info("\t+ vo : {}", vo);
		
	} // testSelectUser
	
	
	@Test
	@Order(2)
	@DisplayName("2. testUpdateUserWithRememberMe")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testUpdateUserWithRememberMe() throws Exception {
		log.info("testSelectUser() invoked.");

		String userid = "USER_1";
		String rememberme = "ACC6CC2F7C5BB36C89701FB4E5BF5287";
		
		final int maxAge = 1*60*60*24*7;         	// 1주일
		long now = System.currentTimeMillis();
		long expiry = now + (maxAge * 1000L); 		// 자동로그인 쿠키의 만료일자!!!
		
		Timestamp rememberage = new Timestamp(expiry); // 만료일자 계산
		
		int updateCount = this.dao.updateUserWithRememberMe(userid, rememberme, rememberage);
		log.info("\t+ updateCount : {}", updateCount);
		
	} // testUpdateUserWithRememberMe
	
//	@AfterEach
//	@AfterAll
		
	
} // end class
