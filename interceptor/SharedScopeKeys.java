package org.zerock.myapp.common;

public final class SharedScopeKeys {
	
	
	public static final String LOGIN_KEY = "__LOGIN__";
	public static final String LOGIN_RESULT = "__RESULT__";
	public static final String USER_KEY = "__USER__";             // Session scope에 UserVO 인증정보객체 바인딩시 사용
	public static final String REMEMBERME_KEY = "__REMEMBERME__"; // 자동로그인 쿠키의 이름으로 사용

} // end class
