package com.clzmall.admin.mapper;

import java.util.List;

import com.clzmall.common.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {
	
	/**
	 * 根据邮箱查询
	 * @param id
	 * @return
	 */
	List<SysUser> selectByAccount(String account);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	SysUser selectByPrimaryKey(Integer id);
	
	/**
	 * 选择更新
	 * @param vo
	 * @return
	 */
	int updateSelective(@Param("vo") SysUser vo);
	
	/**
	 * 查询全部
	 * @return
	 */
	List<SysUser> selectAll(@Param("account") String account);
	
	/**
	 * 保存用户
	 * @return
	 */
	int saveUser(@Param("vo") SysUser vo);
}
