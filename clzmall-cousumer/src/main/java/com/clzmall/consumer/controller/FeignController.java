package com.clzmall.consumer.controller;

import com.clzmall.api.service.AddressServiceApi;
import com.clzmall.consumer.api.RespMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by bairong on 2018/12/27.
 */
public class FeignController {

    @Autowired
    private AddressServiceApi addressServiceApi;

    @GetMapping(value = "/consumer/address/listAddress")
    public RespMsg listAddress(Integer uid) {


    }
}
