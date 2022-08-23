package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value // VO는 Value, DTO와 자바빈즈는 Data
public class BoardVO {
	// 변수명은 자바 식별자 규칙에 맞게 만들어주자
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	
	// 정보통신망법의 요구사항에 따라 중요 데이터 테이블에는
	// 아래와 같이 레코드가 최초 생성된 시각과 최종 수정된 시각을 유지할 수 있게 컬럼 정의
	private Date insertTs;
	private Date updateTs;	

} // end class
