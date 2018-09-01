package com.clzmall.app.entity.vo;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@Data
@ToString
public class PayVo {

    private String timeStamp;
    private String nonceStr;
    private String prepayId;
    private String sign;
}
