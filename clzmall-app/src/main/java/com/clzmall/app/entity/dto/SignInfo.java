package com.clzmall.app.entity.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.ToString;

/**
 * Created by bairong on 2018/10/11.
 */
@Data
@ToString
public class SignInfo {

    private String appId;//小程序ID
    private String timeStamp;//时间戳
    private String nonceStr;//随机串
    @XStreamAlias("package")
    private String repay_id;
    private String signType;//签名方式


}
