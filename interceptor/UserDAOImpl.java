package org.zerock.myapp.persistence;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.LoginDTO;
import org.zerock.myapp.domain.UserVO;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Repository
public class UserDAOImpl implements UserDAO {
	
	
	// 주입
	@Setter(onMethod_=@Autowired)
	private SqlSessionFactory sqlSessionFactory;

	// 1. 로그인 창에서 입력한 아이디/암호에 해당하는 사용자 정보 획득
	@Override
	public UserVO selectUser(LoginDTO dto) throws Exception {
		log.trace("selectUser({}) invoked.", dto);
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try(sqlSession){
			String namespace = "org.zerock.myapp.persistence.UserDAO";
			String sqlId = "selectUser";
			
			String sql = namespace + "." + sqlId;
			
			return sqlSession.<UserVO>selectOne(sql, dto);
		} // try-with-resources
		
	} // selectUser

	@Override
	public int updateUserWithRememberMe(String userid, String rememberme, Timestamp rememberage) throws Exception {
		log.trace("updateUserWithRememberMe({}, {}, {}) invoked.", userid, rememberme, rememberage);
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try(sqlSession){
			String namespace = "org.zerock.myapp.persistence.UserDAO";
			String sqlId = "updateUserWithRememberMe";
			
			String sql = namespace + "." + sqlId;
			
			
			// 어떻게 여러 매개변수의 값을 SQL 문장의 바인드 변수에 전달할까?
			// (1) 자바빈 객체로 전달 => MyBatis는 SQL 문장의 바인드 변수명 (#{이름}) 앞에 'get'을 붙여서,
			//                             get + 바인드 변수명해서, get 바인드 변수명() 메소드 호출해서 값을 얻어냄
			// (2) Map 객체로 전달 =>  MyBatis는 SQL 문장의 바인드 변수명 (#{이름})을 Map의 key로 사용
			//                         즉, map.get(바인드 변수명)으로 값을 얻어냄
			
			Map<String, Object> params = new HashMap<>();
			params.putIfAbsent("userid", userid);
			params.putIfAbsent("rememberme", rememberme);
			params.putIfAbsent("rememberage", rememberage);
			
			return sqlSession.update(sql, params);
		} // try-with-resources} 
	
	} // updateUserWithRememberMe
	

	@Override
	public UserVO selectUSerByRememberMeCookie(String rememberMeCookie) throws Exception {
		log.trace("selectUSerByRememberMeCookie({}) invoked.", rememberMeCookie);

		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try(sqlSession){
			String namespace = "org.zerock.myapp.persistence.UserDAO";
			String sqlId = "selectUSerByRememberMeCookie";
			
			String sql = namespace + "." + sqlId;
			
			return sqlSession.<UserVO>selectOne(sql, rememberMeCookie);
		} // try-with-resources
		
	
	} // selectUSerByRememberMeCookie

} // end class
