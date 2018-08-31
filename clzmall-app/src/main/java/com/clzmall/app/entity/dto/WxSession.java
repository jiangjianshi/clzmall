package com.clzmall.app.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangjianshi on 18/8/4.
 */
@Data
@ToString
public class WxSession {

    private String session_key;
    private String openid;

}
