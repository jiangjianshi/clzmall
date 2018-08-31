package com.clzmall.app.entity.dto;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jiangjianshi on 18/8/18.
 */
@Data
@ToString
public class OrderDto {

    private double score;
    private int isNeedLogistics;
    private double amountTotle;
    private double amountLogistics;
}
