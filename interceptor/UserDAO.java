package org.zerock.myapp.persistence;

import java.sql.Timestamp;

import org.zerock.myapp.domain.LoginDTO;
import org.zerock.myapp.domain.UserVO;

public interface UserDAO {
	
	// 1. 로그인 창에서 입력한 아이디/암호에 해당하는 사용자 정보 획득
	public abstract UserVO selectUser(LoginDTO dto) throws Exception;
	
	// 2. 자동로그인 쿠키값 (rememberme 컬럼)과 이 쿠키의 만료일자 (rememberage) 설정
	public abstract int updateUserWithRememberMe(String userid, String rememberme, Timestamp rememberage) throws Exception;
	
	// 3. 인자 값으로 받은 자동로그인 쿠키값으로 UserVO 객체를 만들어 반환
	public abstract UserVO selectUSerByRememberMeCookie(String rememberMeCookie) throws Exception;

} // end class
