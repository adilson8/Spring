package org.zerock.myapp.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.mapper.Sample1Mapper;
import org.zerock.myapp.mapper.Sample2Mapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class SampleTXServiceImpl implements SampleTXService, InitializingBean {
	
	 // 주입
	@Setter(onMethod_= @Autowired)
	private Sample1Mapper mapper1;
	
	 // 주입
	@Setter(onMethod_= @Autowired)
	private Sample2Mapper mapper2;
		

	@Transactional // TX 처리 수행 (All or Nothing)
	@Override
	public void 계좌이체(String data) throws Exception {
		log.info("계좌이체() invoked.");
		
		
		// 1. 소스계좌에서 이체금액만큼 차감 (DML 문장수행)
		this.mapper1.insertCol(data);
		
		// 2. 타겟계좌로 이체금액만큼 추가 (DML 문장수행)
		this.mapper2.insertCol(data);
		
	} // 계좌이체


	@Override
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet() invoked.");
		
		log.info("\t+ this.mapper1 : {}, type : {}", this.mapper1, this.mapper1.getClass().getName());
		log.info("\t+ this.mapper2 : {}, type : {}", this.mapper2, this.mapper2.getClass().getName());
	} // afterPropertiesSet

} // end class
