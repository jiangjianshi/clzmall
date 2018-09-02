package com.clzmall.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.clzmall.app.entity.dto.TemplateMsgDto;
import com.clzmall.app.mapper.TemplateMsgMapper;
import com.clzmall.app.mapper.WxUserMapper;
import com.clzmall.app.service.TemplateMsgService;
import com.clzmall.app.util.WxUtil;
import com.clzmall.common.model.TemplateMsg;
import com.clzmall.common.model.WxUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by jiangjianshi on 18/9/2.
 */
@Slf4j
@Service
@Transactional
public class TemplateMsgServiceImpl implements TemplateMsgService {

    @Resource
    private TemplateMsgMapper templateMsgMapper;
    @Resource
    private WxUserMapper wxUserMapper;


    @Override
    public int saveTemplateMsgInfo(TemplateMsg msgInfo) {
        return templateMsgMapper.insert(msgInfo);
    }

    @Override
    public boolean sendTemplateMsgToUser(TemplateMsg msg) {

        WxUser user = wxUserMapper.selectByPrimaryKey(msg.getUid());
        TemplateMsgDto msgDto = new TemplateMsgDto();
        msgDto.setTouser(user.getOpenId());
        msgDto.setPage(msg.getUrl());
        msgDto.setTemplate_id(msg.getTemplateId());
        msgDto.setForm_id(msg.getFormId());
        msgDto.setData(JSON.parseObject(msg.getPostJsonString()));

        boolean result = WxUtil.sendTemplateMsg(msgDto);
        if (result) {
            TemplateMsg templateMsg = new TemplateMsg();
            templateMsg.setId(msg.getId());
            templateMsg.setType(1);
            templateMsg.setUpdateTime(new Date());
            int count = templateMsgMapper.updateSelective(templateMsg);
            log.info("更新信息：{}, 状态：{}", templateMsg.toString(), count);
            return result;
        } else {
            return false;
        }
    }

}
