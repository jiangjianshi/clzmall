package com.clzmall.app.mapper;

import com.clzmall.common.model.TemplateMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@Repository
public interface TemplateMsgMapper {

    int insert(@Param("vo") TemplateMsg msg);

    TemplateMsg selectByPrimaryKey(Integer id);
}
