package com.clzmall.app.entity.vo;

import com.clzmall.app.entity.dto.GoodsDto;
import com.clzmall.common.model.Orders;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangjianshi on 18/8/19.
 */
public class OrderListVo {

    private List<Orders> orders;
    private Map<Integer, List<GoodsDto>> goodsMap;


    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public Map<Integer, List<GoodsDto>> getGoodsMap() {
        return goodsMap;
    }

    public void setGoodsMap(Map<Integer, List<GoodsDto>> goodsMap) {
        this.goodsMap = goodsMap;
    }
}
