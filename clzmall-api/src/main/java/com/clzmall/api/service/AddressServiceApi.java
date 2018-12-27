package com.clzmall.api.service;

import com.clzmall.api.entity.RespMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by bairong on 2018/12/27.
 */
@FeignClient(value = "clzmall-app", fallbackFactory = AddressServiceFallbackFactory.class)
public interface AddressServiceApi {

    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    RespMsg get(@PathVariable("id") Integer id);
}
