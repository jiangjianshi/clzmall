package com.clzmall.app.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@Data
@ToString
public class PayParam {

    private String uid;
    private double money;
    private String remark;
    private String payName;
    private String nextAction;


}
