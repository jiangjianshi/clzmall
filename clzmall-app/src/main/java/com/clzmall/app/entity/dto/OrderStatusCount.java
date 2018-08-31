package com.clzmall.app.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangjianshi on 18/8/5.
 */

@Data
@ToString
public class OrderStatusCount {

    private long noPay;
    private long noSend;
    private long noReceive;
    private long noComment;
    private long done;
}
