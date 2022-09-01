package org.zerock.myapp.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/RESTful/")
@RestController
public class PathVariableController {

	// RESTful 방식의 Request Mapping => Request URI(대상특정자원) + HTTP method(C/R/U/D)
	// CREATE(PUT), READ(GET), UPDATE(POST), DELETE(DELETE) => 가장 일반적인 방식
	// => 개발주체마다 각 전송방식에 별도의 의미를 부여해서 사용
	// HTTP protocol : 약 15가지 정도의 전송방식이 정의되어 있음.
	@GetMapping(
		path="/product/{category}/{productId}", 
		produces=MediaType.APPLICATION_JSON_VALUE
		)
//	public String[] getPathVariable(   // 배열로 보내보자
//	public Map<String, String> getPathVariable(   // Map 객체로 보내보자
	public Set<String> getPathVariable(   		  // Set 객체로 보내보자
			@PathVariable("category") String category,
			@PathVariable("productId") Integer productId
		) {
		log.info("getPathVariable({}, {}) invoked.", category, productId);
		
		String productInfo = "정말 좋은 제품";
		
		// 배열로 보내보자
//		String[] result = { 
//				"category :" + category, 
//				"productId :" + String.valueOf(productId), 
//				"productInfo :" + productInfo 
//				};
		
		// Map 객체로 보내보자
//		Map<String, String> result = new HashMap<>();
//		result.put("category", category);
//		result.put("productId", String.valueOf(productId));
//		result.put("productInfo", productInfo);		
		
		// Set 객체로 보내보자
		Set<String> result = new HashSet<>();
		result.add(category);
		result.add(String.valueOf(productId));
		result.add(productInfo);	
		
		return result;
	} // getPathVariable
	
} // end class
