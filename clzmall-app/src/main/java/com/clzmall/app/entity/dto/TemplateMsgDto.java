package com.clzmall.app.entity.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangjianshi on 18/9/2.
 */
@Data
@ToString
public class TemplateMsgDto {

        private String touser;
        private String template_id;
        private String page;
        private String form_id;
        private JSONObject data;
        private String emphasis_keyword;
}
