package com.clzmall.app.service;

import com.clzmall.app.entity.dto.OrderDto;
import com.clzmall.app.entity.dto.OrderStatusCount;
import com.clzmall.app.entity.dto.PayParam;
import com.clzmall.app.entity.vo.OrderDetailVo;
import com.clzmall.app.entity.vo.OrderListVo;
import com.clzmall.app.entity.vo.PayVo;
import com.clzmall.common.model.Orders;

/**
 * Created by jiangjianshi on 18/8/5.
 */
public interface OrdersService {


    OrderListVo listOrders(Integer uid, Integer status);

    int closeOrder(Integer orderId);

    OrderStatusCount statisticsOrders(Integer uid);

    OrderDto calOrder(Integer uid, String goodsJsonStr, String remark);

    Orders createOrder(Integer uid, String goodsJsonStr, String remark, Integer addressId);

    OrderDetailVo getOrderDetail(Integer orderId);

    int confirmOrder(Integer orderId);

    PayVo getPayData(PayParam payParam);
}
