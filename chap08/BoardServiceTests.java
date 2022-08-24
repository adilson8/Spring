package org.zerock.myapp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
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
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.exception.ServiceException;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
// JUnit 4,5 모두 반드시 매개변수 없는 기본 생성자를 가져야함
@NoArgsConstructor

// JUnit5 방식
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardServiceTests {
	
	// 주입하자!
	@Setter(onMethod_= {@Autowired})
	private BoardService service;
	
	// 주입이 되었는지 1회성 전처리 과정을 통해 확인하자
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		// 주입이 되었는지 확인
		assertNotNull(this.service);
		
		log.info("\t+ this.service : {}", this.service);
	} // beforeAll
	
//	@BeforeEach

	@Test
	@Order(1)
	@DisplayName("1. BoardService.getList")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testGetList() throws ServiceException {
		log.trace("testGetList() invoked.");
		
		@Cleanup("clear")
		List<BoardVO> list = this.service.getList();
		
		list.forEach(log::info);
	} // testGetList
	
	
	@Test
	@Order(2)
	@DisplayName("2. BoardService.register")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testRegister() throws ServiceException {
		log.trace("testRegister() invoked.");
		
		// BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		dto.setTitle("TITLE_NEW");
		dto.setContent("CONTENT_NEW");
		dto.setWriter("WRITER_NEW");
		
		log.info("\t+ : result : {}", this.service.register(dto) ); 
				
	} // testRegister
	
	
	@Test
	@Order(3)
	@DisplayName("3. BoardService.modify")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testModify() throws ServiceException {
		log.trace("testModify() invoked.");
		
		// BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		dto.setBno(150);
		dto.setTitle("TITLE_UPDATED");
		dto.setContent("CONTENT_UPDATED");
		dto.setWriter("WRITER_UPDATED");
		
		log.info("\t+ : result : {}", this.service.modify(dto) ); 
				
	} // testModify
	
	
	@Test
	@Order(4)
	@DisplayName("4. BoardService.remove")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testRemove() throws ServiceException {
		log.trace("testRemove() invoked.");
		
		// BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		dto.setBno(151);
		
		log.info("\t+ : result : {}", this.service.remove(dto) ); 
				
	} // testRemove
	
	
	@Test
	@Order(5)
	@DisplayName("4. BoardService.get")
	@Timeout(value = 10, unit = TimeUnit.SECONDS)
	void testGet() throws ServiceException {
		log.trace("testGet() invoked.");
		
		// BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		dto.setBno(153);
		
		log.info("\t+ : BoardVO : {}", this.service.get(dto) ); 
				
	} // testGet
	
//	@AfterEach
//	@AfterAll

} // end class
