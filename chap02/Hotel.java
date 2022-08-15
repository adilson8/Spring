package org.zerock.myapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor   // 모든 생성자는 명시적으로 보이게 하자 (기본 생성자는 안 만들어도 컴파일러가 자동으로 생성하지만 우리가 만들어주자)

//@Component("hotel") 
                     // 관례에 따라 논리적인 이름은 타입명의 첫문자를 소문자로 한다
public class Hotel { // POJO : Plain Old Java Object
	
//	@Resource(type=Chef.class)
//	@Resource	
//	@Inject	
//	@Autowired
	
	@Setter(onMethod_= { @Autowired })
	private Chef chef;
	
} // end class
