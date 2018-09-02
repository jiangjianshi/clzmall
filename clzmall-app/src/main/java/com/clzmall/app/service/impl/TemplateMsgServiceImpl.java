package com.clzmall.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clzmall.app.entity.dto.TemplateMsgDto;
import com.clzmall.app.mapper.TemplateMsgMapper;
import com.clzmall.app.service.TemplateMsgService;
import com.clzmall.common.common.WxConsts;
import com.clzmall.common.model.TemplateMsg;
import com.clzmall.common.util.HttpUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by jiangjianshi on 18/9/2.
 */
@Slf4j
@Service
@Transactional
public class TemplateMsgServiceImpl implements TemplateMsgService {

    @Resource
    private TemplateMsgMapper templateMsgMapper;


    @Override
    public int saveTemplateMsgInfo(TemplateMsg msgInfo) {
        return templateMsgMapper.insert(msgInfo);
    }

}
