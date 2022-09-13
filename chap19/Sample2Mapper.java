package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface Sample2Mapper {

	@Insert("INSERT INTO tbl_sample2 (col) VALUES ( #{col} )")
	public abstract void insertCol( @Param("col") String data) throws Exception;
	
} // end interface
