package org.zerock.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.EmployeeVO;

public interface EmployeesMapper {

	// 방법1) 어노테이션 방식
	@Select("SELECT * FROM employees WHERE employee_id > 0")
	public abstract List<EmployeeVO> getAllEmployees();
	
	// 마이바티스는 아래의 자동실행규칙을 따르는 경우
	// (1) Mapper Interface의 패키지와 동일한 폴더구조를 생성하라
	// (2) (1)에서 생성한 폴더 아래에 Mapper Interface의 타입명과 동일한 이름의 Mapper XML 파일을 생성하라
	// (3) (2)에서 생성한 Mapper XML 파일의 namespace 속성의 값은 Mapper Interface의 FCQN으로 한다
	// (4) (2)에서 생성한 Mapper XML 파일에 등록한 SQL 문장의 id 속성의 값은 Mapper Interface의 추상메소드 이름과 동일하게 지어라
	
	// 방법2) 자동실행규칙 방식
	public abstract List<String> getAllEmployeesNames();

} // end interface
