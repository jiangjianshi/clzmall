package com.clzmall.consumer.controller;

import com.clzmall.api.entity.RespMsg;
import com.clzmall.api.service.AddressServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bairong on 2018/12/27.
 */
@RestController
public class FeignController {

    @Autowired
    private AddressServiceApi addressServiceApi;

    @GetMapping(value = "/consumer/address/listAddress")
    public RespMsg listAddress(Integer uid) {

        return addressServiceApi.get(uid);
    }
}
