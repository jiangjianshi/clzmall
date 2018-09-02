package com.clzmall.common.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@Data
@ToString
public class TemplateMsg {

    private Integer id; //
    private Integer uid; //
    private Integer type; //
    private String module; //
    private Integer businessId; //
    private Integer triggerType; //
    private String templateId; //
    private String formId; //
    private String url; //
    private String postJsonString; //
    private Date createTime; //
    private Date updateTime; //
}
