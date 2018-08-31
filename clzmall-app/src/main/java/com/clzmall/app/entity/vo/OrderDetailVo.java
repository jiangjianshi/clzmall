package com.clzmall.app.entity.vo;

import com.clzmall.app.entity.dto.GoodsDto;
import com.clzmall.common.model.Logistics;
import com.clzmall.common.model.Orders;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/19.
 */
@Data
@ToString
public class OrderDetailVo {

    private Orders orderInfo;
    private Logistics logistics;
    private List<GoodsDto> goods;

}
