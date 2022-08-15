package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
		
	// 방법1) 어노테이션 방식 (바로 테스트 메소드에서 사용가능)
	@Select("SELECT to_char(sysdate, 'yyyy/MM/dd HH24:mi:ss') AS now FROM dual")
	public abstract String getCurrentTime1();
	
	// 방법2) 자동실행규칙 방식 (TimeMapper.xml 만들어줘야함)
	// Mapper XML 파일의 SQL 문장으로 현재시간 반환	
	public abstract String getCurrentTime2();

} // end interface
