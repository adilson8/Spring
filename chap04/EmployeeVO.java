package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value // 한번에 vo클래스로 만들어주는 어노테이션 cf) DTO, 자바빈즈는 @Data
public class EmployeeVO {

	// 0) 테이블의 컬럼의 순서와 필드의 순서를 동일하게 작성한다. (***순서*** 타입 이름 모두 동일해야함!!!)
	// 1) VO class의 필드는 외부에서 접근하지 못하게 private으로 한다.
	// 2) 테이블의 컬럼은 NULL (결측치)가 있을 수 있기 때문에 기본 타입인 int가 아니고 
	//    결측치를 표현할 수 있는 Integer로 필드를 선언해야한다.
	
	// 자바 식별자 규칙에 따르면 파이널 상수만이 모두 대문자로 표기하기 때문에 
    // 소문자로 바꿔주고 카멜기법도 적용하자
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Date hireDate;
	private String jobId;
	private Integer salary;
	private Integer commissionPct;
	private Integer managerId;
	private Integer departmentId;
	
} // end class
