package com.clzmall.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.clzmall.app.entity.dto.*;
import com.clzmall.app.entity.vo.OrderDetailVo;
import com.clzmall.app.entity.vo.OrderListVo;
import com.clzmall.app.entity.vo.PayVo;
import com.clzmall.app.mapper.OrderGoodsRelationMapper;
import com.clzmall.app.mapper.OrdersMapper;
import com.clzmall.app.service.OrdersService;
import com.clzmall.app.util.HttpRequest;
import com.clzmall.app.util.RandomStringGenerator;
import com.clzmall.app.util.Signature;
import com.clzmall.common.common.WxConsts;
import com.clzmall.common.enums.OrderTypeEnum;
import com.clzmall.common.model.OrderGoodsRelation;
import com.clzmall.common.model.Orders;
import com.clzmall.common.model.TemplateMsg;
import com.clzmall.common.util.DateUtil;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jiangjianshi on 18/8/5.
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {


    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderGoodsRelationMapper orderGoodsRelationMapper;


    //交易类型
    private final String trade_type = "JSAPI";
    // 统一下单API接口链接
    private final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    @Override
    public OrderListVo listOrders(Integer uid, Integer status) {

        OrderListVo vo = new OrderListVo();
        List<Orders> orders = ordersMapper.selectByUidAndStatus(uid, status);

        List<Integer> ids = orders.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<GoodsDto> gsDto = orderGoodsRelationMapper.selectGoodsByOrderIds(ids);
        Map<Integer, List<GoodsDto>> map = gsDto.stream().collect(Collectors.groupingBy(x -> x.getOrderId()));

        vo.setOrders(orders);
        vo.setGoodsMap(map);
        return vo;
    }

    @Override
    public int closeOrder(Integer orderId) {

        Orders order = new Orders();
        order.setId(orderId);
        order.setStatus(-1);
        return ordersMapper.updateSelective(order);
    }

    @Override
    public OrderStatusCount statisticsOrders(Integer uid) {

        List<Orders> list = ordersMapper.selectByUidAndStatus(uid, null);
        Map<Integer, Long> groupMap = list.stream().collect(
                Collectors.groupingBy(x -> x.getStatus(), Collectors.counting()));

        OrderStatusCount statusCount = new OrderStatusCount();
        statusCount.setNoPay(groupMap.get(0) == null ? 0 : groupMap.get(0));
        statusCount.setNoSend(groupMap.get(1) == null ? 0 : groupMap.get(1));
        statusCount.setNoReceive(groupMap.get(2) == null ? 0 : groupMap.get(2));
        statusCount.setNoComment(groupMap.get(3) == null ? 0 : groupMap.get(3));
        statusCount.setDone(groupMap.get(4) == null ? 0 : groupMap.get(4));

        return statusCount;
    }

    @Override
    public OrderDto calOrder(Integer uid, String goodsJsonStr, String remark) {

        OrderDto dto = new OrderDto();
        dto.setAmountLogistics(12);
        dto.setAmountTotle(100);
        dto.setIsNeedLogistics(1);
        dto.setScore(12);
        return dto;
    }


    @Override
    public Orders createOrder(Integer uid, String goodsJsonStr, String remark, Integer addressId) {

        Orders order = new Orders();
        order.setUid(uid);
        order.setAddressId(addressId);
        order.setOrderCode(DateUtil.formatDateTime(new Date()) + new Random().nextInt(10000));
        order.setRealAmount(new BigDecimal("99"));
        order.setStatus(0);
        order.setRemark(remark);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        int cnt = ordersMapper.insert(order);

        List<UserOrderDto> orders = JSON.parseArray(goodsJsonStr, UserOrderDto.class);
        for (UserOrderDto orderDto : orders) {

            OrderGoodsRelation odr = new OrderGoodsRelation();
            odr.setGoodsId(orderDto.getGoodsId());
            odr.setOrderId(order.getId());
            odr.setNumber(orderDto.getNumber());
            odr.setGoodsProperties(orderDto.getPropertyChildIds());
            odr.setLabel(orderDto.getLabel().trim());
            odr.setLogisticsType(orderDto.getLogisticsType());
            odr.setInviterId(orderDto.getInviter_id());
            odr.setStatus(1);
            orderGoodsRelationMapper.insert(odr);
        }

        return order;
    }


    @Override
    public OrderDetailVo getOrderDetail(Integer orderId) {
        Orders orderInfo = ordersMapper.selectByPrimaryKey(orderId);
        List<GoodsDto> gsDto = orderGoodsRelationMapper.selectGoodsByOrderIds(Lists.newArrayList(orderId));

        OrderDetailVo detailVo = new OrderDetailVo();
        detailVo.setOrderInfo(orderInfo);
        detailVo.setGoods(gsDto);
        return detailVo;
    }

    @Override
    public int confirmOrder(Integer orderId) {

        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setStatus(OrderTypeEnum.DONE.getCode());
        return ordersMapper.updateSelective(orders);
    }

    @Override
    public PayVo getPayData(PayParam payParam) {
        String money = "10";
        String title = "家具1";
        try {
            OrderInfo order = new OrderInfo();
            order.setAppid(WxConsts.APPID);
            order.setMch_id(WxConsts.MCH_ID);
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setBody(title);
            order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
            order.setTotal_fee(Integer.parseInt(money));
            // 该金钱其实10 是 0.1元
            order.setSpbill_create_ip("172.30.5.82");
            order.setNotify_url("http://www.weixin.qq.com/wxpay/pay.php");
            order.setTrade_type(trade_type);
            order.setOpenid("oVxip5d2A2HOjH0VP_YYOGLG6D2o");
            order.setSign_type("MD5");
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            String result = HttpRequest.sendPost(url, order);
            System.out.println(result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
            // 二次签名
            if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
                SignInfo signInfo = new SignInfo();
                signInfo.setAppId(WxConsts.APPID);
                long time = System.currentTimeMillis() / 1000;
                signInfo.setTimeStamp(String.valueOf(time));
                signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
                signInfo.setRepay_id("prepay_id=" + returnInfo.getPrepay_id());
                signInfo.setSignType("MD5");
                //生成签名
                String sign1 = Signature.getSign(signInfo);
                PayVo paydata = new PayVo();
                paydata.setNonceStr(signInfo.getNonceStr());
                paydata.setTimeStamp(signInfo.getTimeStamp());
                paydata.setPrepayId(signInfo.getRepay_id());
                paydata.setSign(sign1);
                return paydata;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("统一下单失败");
        }
        return null;
    }

}