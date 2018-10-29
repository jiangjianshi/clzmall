package com.clzmall.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.clzmall.app.entity.dto.*;
import com.clzmall.app.entity.vo.OrderDetailVo;
import com.clzmall.app.entity.vo.OrderListVo;
import com.clzmall.app.entity.vo.PayVo;
import com.clzmall.app.mapper.GoodsMapper;
import com.clzmall.app.mapper.OrderGoodsRelationMapper;
import com.clzmall.app.mapper.OrdersMapper;
import com.clzmall.app.service.GoodsService;
import com.clzmall.app.service.OrdersService;
import com.clzmall.app.service.WxUserService;
import com.clzmall.app.util.HttpRequest;
import com.clzmall.app.util.Signature;
import com.clzmall.app.util.WXPayUtil;
import com.clzmall.common.common.WxConsts;
import com.clzmall.common.enums.OrderTypeEnum;
import com.clzmall.common.model.OrderGoodsRelation;
import com.clzmall.common.model.Orders;
import com.clzmall.common.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by jiangjianshi on 18/8/5.
 */
@Slf4j
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {


    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderGoodsRelationMapper orderGoodsRelationMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private WxUserService wxUserService;

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
    public int updateOrder(Orders order) {
        if (StringUtils.isNotEmpty(order.getOrderCode())) {
            return ordersMapper.updateStatusByOrderCode(order.getOrderCode(), order.getStatus());
        } else if (order.getId() != null) {
            return ordersMapper.updateSelective(order);
        } else {
            throw new RuntimeException("参数异常");
        }

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

        List<UserOrderDto> orders = JSON.parseArray(goodsJsonStr, UserOrderDto.class);
        double totalFee = 0;
        for (UserOrderDto order : orders) {
            PriceDto priceDto = goodsService.calSelectedPrice(order.getGoodsId(), order.getPropertyChildIds());
            totalFee += priceDto.getPrice() * order.getNumber();
        }
        Integer score = wxUserService.getUserScore(String.valueOf(uid));
        OrderDto dto = new OrderDto();
        dto.setAmountLogistics(0);
        dto.setAmountTotle(totalFee - score);
        dto.setIsNeedLogistics(0);
        dto.setScore(score);
        return dto;
    }


    @Override
    public Orders createOrder(Integer uid, String goodsJsonStr, String remark, Integer addressId) {

        List<UserOrderDto> orders1 = JSON.parseArray(goodsJsonStr, UserOrderDto.class);
        double totalFee = 0;
        for (UserOrderDto order : orders1) {
            PriceDto priceDto = goodsService.calSelectedPrice(order.getGoodsId(), order.getPropertyChildIds());
            totalFee += priceDto.getPrice() * order.getNumber();
        }
        totalFee -= wxUserService.getUserScore(String.valueOf(uid));
        Orders order = new Orders();
        order.setUid(uid);
        order.setAddressId(addressId);
        order.setOrderCode(DateUtil.formatDateTime(new Date()) + new Random().nextInt(10000));
        order.setRealAmount(new BigDecimal(totalFee));
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
        try {
            Map signData = Maps.newHashMap();
            signData.put("appid", WxConsts.APPID);
            signData.put("mch_id", WxConsts.MCH_ID);
            signData.put("nonce_str", WXPayUtil.generateRandomStr());
            signData.put("body", payParam.getRemark());
            signData.put("out_trade_no", WXPayUtil.generateRandomStr());
//            signData.put("total_fee", payParam.getMoney().multiply(new BigDecimal(100)).toString());
            signData.put("total_fee", "1");
            signData.put("spbill_create_ip", "192.168.2.115");
            signData.put("notify_url", "https://www.weixin.qq.com/wxpay/pay.php");
            signData.put("trade_type", trade_type);
            signData.put("openid", "oVxip5d2A2HOjH0VP_YYOGLG6D2o");

            String result = HttpRequest.sendPost(url, signData, WxConsts.PAY_SECRET);
            log.info("统一下单返回结果：{}", result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
            // 二次签名
            if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
                SignInfo signInfo = new SignInfo();
                signInfo.setAppId(WxConsts.APPID);
                signInfo.setTimeStamp(String.valueOf(WXPayUtil.getCurrentTimestamp()));
                signInfo.setNonceStr(WXPayUtil.generateRandomStr());
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