package com.simple.basic.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper//스프링 부트에서는 매퍼 인터페이스에 매퍼 어노테이션을 반드시 붙여야 한다.
public interface TestMapper {

	public String getTime();
	
}