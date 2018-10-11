package com.clzmall.app.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by bairong on 2018/10/11.
 */
@Data
@ToString
public class OrderReturnInfo {

    private String return_code;
    private String return_msg;
    private String result_code;
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String prepay_id;
    private String trade_type;

}
