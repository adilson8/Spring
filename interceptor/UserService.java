package org.zerock.myapp.service;

import java.sql.Timestamp;

import org.zerock.myapp.domain.LoginDTO;
import org.zerock.myapp.domain.UserVO;

public interface UserService {
	
	// 1. 전송받은 사용자의 아이디와 암호로 사용자 정보를 획득
	public abstract UserVO findUser(LoginDTO dto) throws Exception;
	
	// 2. 자동로그인 쿠키값 (rememberme 컬럼)과 이 쿠키의 만료일자 (rememberage) 설정
	public abstract boolean modifyUserWithRememberMe(String userid, String rememberme, Timestamp rememberage) throws Exception;
	
	// 3. 인자 값으로 받은 자동로그인 쿠키값으로 UserVO 객체를 만들어 반환
	public abstract UserVO findUSerByRememberMeCookie(String rememberMeCookie) throws Exception;
	
} // end class
