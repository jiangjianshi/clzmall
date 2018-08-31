package com.clzmall.admin.mapper;


import com.clzmall.common.common.CodeType;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CodeTypeMapper {
	
	
	List<CodeType> selectByTypeCode(String typeCode);
}
