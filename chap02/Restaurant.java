package org.zerock.myapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
@NoArgsConstructor

@Component("restaurant")
public class Restaurant {
		
//	@Resource(type=Chef.class)
//	@Resource	
//	@Inject	
//	@Autowired
	
	// 아래 어노테이션의 의미 : 아래 필드에 대해, setter 메소드를 만들고 그 위에 지정된 어노테이션을 붙임
	@Setter(onMethod_= { @Autowired }) // 이 필드에 대한 setter 메소드를 통한 의존성 주입 발생
	private Chef chef;
	
	// 생성자를 통한 주입
//	@Autowired
//	public Restaurant(Chef chef) {
//		log.trace("constructor({}) invoked.", chef);
//		
//		this.chef = chef;		// 초기화
//	} // constructor
	
	// setter 메소드를 통한 주입 (setter 메소드 직접 생성)
//	@Autowired
//	public void setChef(Chef chef) {
//		log.trace("setChef({}) invoked.", chef);
//		
//		this.chef = chef;		// 설정 (변경)
//	} // setChef
	

} // end class
