package com.clzmall.admin.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling // 启用定时任务
public class SendTemplateMessage {



//    @Scheduled(cron = "0 */59 * * * ?") // 每59分钟执行一次
    public void test() throws Exception {
    }

}
