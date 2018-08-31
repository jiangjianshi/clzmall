package com.clzmall.app.controller;

import com.clzmall.app.service.OrdersService;
import com.clzmall.app.entity.dto.OrderDto;
import com.clzmall.app.entity.dto.OrderStatusCount;
import com.clzmall.app.entity.vo.OrderDetailVo;
import com.clzmall.app.entity.vo.OrderListVo;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangjianshi on 18/8/5.
 */
@Slf4j
@RestController
@RequestMapping("/orders/")
public class OrdersController extends BaseController {

    @Autowired
    private OrdersService ordersService;


    @RequestMapping("listOrders")
    public RespMsg<OrderListVo> listOrders(Integer uid, Integer status) {
        OrderListVo orderListVo = ordersService.listOrders(uid, status);
        return success("获取成功", orderListVo);
    }


    @RequestMapping("closeOrder")
    public RespMsg<Integer> closeOrder(Integer orderId) {
        int cnt = ordersService.closeOrder(orderId);
        if (cnt == 1) {
            return success("关闭成功", cnt);
        } else {
            return fail("操作失败");
        }
    }


    @RequestMapping("statistics")
    public RespMsg<OrderStatusCount> statistics(Integer uid) {
        OrderStatusCount statusCount = ordersService.statisticsOrders(uid);
        return success("获取成功", statusCount);
    }


    @RequestMapping("createOrder")
    public RespMsg<Object> createOrder(Integer uid, String goodsJsonStr, String remark, String calculate, Integer addressId) {

        try {
            if ("true".equals(calculate)) {
                OrderDto dto = ordersService.calOrder(uid, goodsJsonStr, remark);
                return success("下单成功", dto);
            } else {
                Orders order = ordersService.createOrder(uid, goodsJsonStr, remark, addressId);
                return success("下单成功", order);
            }

        } catch (Exception e) {
            log.error("下单失败", e);
            return fail("下单失败");
        }
    }

    @RequestMapping("orderDetail")
    public RespMsg<OrderDetailVo> orderDetail(Integer orderId) {

        try {
            OrderDetailVo detailVo = ordersService.getOrderDetail(orderId);
            return success("获取成功", detailVo);
        } catch (Exception e) {
            log.error("获取订单详情失败", e);
            return fail("获取订单详情失败");
        }
    }

    @RequestMapping("confirmOrder")
    public RespMsg<Integer> confirmOrder(Integer orderId) {

        try {
            int cnt =  ordersService.confirmOrder(orderId);
            return success("获取成功", cnt);
        } catch (Exception e) {
            log.error("确认收货失败", e);
            return fail("确认收货失败");
        }
    }

}
