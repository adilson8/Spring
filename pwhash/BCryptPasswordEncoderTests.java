package org.zerock.myapp.pwhash;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BCryptPasswordEncoderTests {
	
	@Disabled
	@Test
	@Order(1)
	@DisplayName("1. testBCryptPasswordEncoder")
	@Timeout(value = 10, unit =TimeUnit.SECONDS)
	void testBCryptPasswordEncoder() {
		log.trace("testBCryptPasswordEncoder() invoked.");
		
		String password = "John1234!!";  // Plain Text
			
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String cipherText = encoder.encode(password);
		log.info("\t+ cipherText : {}", cipherText);
				
	} // testBCryptPasswordEncoder
	
	
	@Disabled
	@Test
	@Order(2)
	@DisplayName("2. testMatches")
	@Timeout(value = 10, unit =TimeUnit.SECONDS)
	void testMatches() {
		log.trace("testMatches() invoked.");
		
		String plainText = "John1234!!";  // Raw Password
			
		// Case1 : 저장된 해쉬값과 입력한 암호의 비교
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String cipherText = encoder.encode(plainText);
//		log.info("\t+ cipherText : {}", cipherText);
//		
////		------------------------------------
// 
//		// plainText : 로그인 창에서 사용자가 입력한 Raw Password
//		// cipherText : 사용자 테이블의 암호컬럼에 저장된 해쉬값
//		boolean isMatched = encoder.matches(plainText, cipherText);
//		log.info("\t+ 2. isMatched : {}", isMatched);
		
		
		// Case2 : 같은 암호(Plain text)에 대해서 여러번 해쉬값을 생성하여 비교
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		for(int i=0; i<10; i++) {
			String cipherText = encoder.encode(plainText);
			log.info("\t+ [{}]. cipherText : {}", i,  cipherText);
			
			boolean isMatched = encoder.matches(plainText, cipherText);
			log.info("\t+ [{}]. isMatched : {}",i,  isMatched);
		} // for
		
				
	} // testMatches
	
	
	@Test
	@Order(3)
	@DisplayName("3. testBCryptPasswordEncoderWithSalt")
	@Timeout(value = 10, unit =TimeUnit.SECONDS)
	void testBCryptPasswordEncoderWithSalt() {
		log.trace("testBCryptPasswordEncoderWithSalt() invoked.");
		
		String password = "John1234!!";  // Plain Text
		String salt = "SALT_";
		
//		암호의 해쉬값 생성시, "salt"추가 권장
//		"Salt" - 사용자가 가입시 입력한 암호 + 내부에서 지정된 추가 문자열
			
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String cipherText = encoder.encode(salt + password);
		log.info("\t+ 1. cipherText : {}", cipherText);
		
//		---
		
		log.info("\t+ 2. isMatchWithSalt : {}", encoder.matches(salt+password, cipherText) );
		
				
	} // testBCryptPasswordEncoderWithSalt
	

} // end class
