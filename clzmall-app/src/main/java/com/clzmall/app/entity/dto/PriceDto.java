package com.clzmall.app.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangjianshi on 18/8/18.
 */
@Data
@ToString
public class PriceDto {

    private double price;
    private int score;
    private int stores;
}
