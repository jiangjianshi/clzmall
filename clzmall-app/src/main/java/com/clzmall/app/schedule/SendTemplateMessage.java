package com.clzmall.app.schedule;

import com.clzmall.app.mapper.TemplateMsgMapper;
import com.clzmall.app.service.TemplateMsgService;
import com.clzmall.common.model.TemplateMsg;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling // 启用定时任务
public class SendTemplateMessage {

    @Resource
    private TemplateMsgMapper TemplateMsgMapper;

    @Resource
    private TemplateMsgService templateMsgService;

//    @Scheduled(fixedRate = 5 * 1000) // 每5秒执行一次
    @Scheduled(cron = "0 */30 * * * ?") // 每30分钟执行一次
    public void test() throws Exception {

        Date ct = DateUtils.addMinutes(new Date(), -3);
        List<TemplateMsg> list = TemplateMsgMapper.selectByTriggerTypeAndCreateTime(-1, ct);
        if (!CollectionUtils.isEmpty(list)) {
            for (TemplateMsg msg : list) {
                try {
                    boolean result = templateMsgService.sendTemplateMsgToUser(msg);
                    log.info("模板消息发送状态: uid={}, result={}", msg.getUid(), result);
                } catch (Exception e) {
                    log.error("模板消息发送异常，uid={}, msgId={}, orderId={}", msg.getUid(), msg.getId(), msg.getBusinessId());
                    e.printStackTrace();
                }
            }
        } else {
            log.info("没有待发送的模板消息");
        }

    }

}
