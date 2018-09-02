package com.clzmall.app.mapper;

import com.clzmall.common.model.TemplateMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@Repository
public interface TemplateMsgMapper {

    int insert(@Param("vo") TemplateMsg msg);

    TemplateMsg selectByPrimaryKey(Integer id);

    List<TemplateMsg> selectByTriggerTypeAndCreateTime(@Param("triggerType") Integer triggerType, @Param("createTime")  Date createTime);

    int updateSelective(@Param("vo") TemplateMsg msg);
}
