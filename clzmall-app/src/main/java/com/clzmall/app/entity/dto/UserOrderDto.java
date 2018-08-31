package com.clzmall.app.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangjianshi on 18/8/18.
 */

@Data
@ToString
public class UserOrderDto {

    private Integer goodsId;
    private Integer number;
    private String propertyChildIds;
    private String label;
    private Integer logisticsType;
    private Integer inviter_id;
}
