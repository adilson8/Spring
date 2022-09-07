package org.zerock.myapp.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.LoginDTO;
import org.zerock.myapp.domain.UserVO;
import org.zerock.myapp.persistence.UserDAO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
//@NoArgsConstructor // 직접주입
@AllArgsConstructor  // 생성자를 통한 자동주입

@Service
public class UserServiceImpl implements UserService {

	// 직접 주입
//	@Setter(onMethod_= @Autowired)
	private UserDAO dao;
	
	@Override
	public UserVO findUser(LoginDTO dto) throws Exception {
		log.trace("findUser({}) invoked.", dto);
		
		
		return this.dao.selectUser(dto);		
	} // findUser
	

	@Override
	public boolean modifyUserWithRememberMe(String userid, String rememberme, Timestamp rememberage) throws Exception {
		log.trace("modifyUserWithRememberMe({}, {}, {}) invoked.", userid, rememberme, rememberage);
		
		return this.dao.updateUserWithRememberMe(userid, rememberme, rememberage) != 1;
	} // modifyUserWithRememberMe  


	@Override
	public UserVO findUSerByRememberMeCookie(String rememberMeCookie) throws Exception {
		log.trace("findUSerByRememberMeCookie({}) invoked.", rememberMeCookie);
		
		
		return this.dao.selectUSerByRememberMeCookie(rememberMeCookie);
	} // findUSerByRememberMeCookie

} // end class
