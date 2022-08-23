package org.zerock.myapp.domain;

import lombok.Data;

@Data // VO는 Value, DTO 자바빈즈는 @Data
public class BoardDTO {
	// 변수명은 자바 식별자 규칙에 맞게 만들어주자
		private Integer bno;
		private String title;
		private String content;
		private String writer;

} // end class
