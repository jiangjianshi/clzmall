package com.clzmall.admin.mapper;

import com.clzmall.common.model.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper {
	
	
	List<SysMenu> selectUserAllRight(Integer userId);
}
