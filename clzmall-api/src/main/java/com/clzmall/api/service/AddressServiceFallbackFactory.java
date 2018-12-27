package com.clzmall.api.service;

import com.clzmall.api.entity.RespMsg;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created by bairong on 2018/12/27.
 */
@Component
public class AddressServiceFallbackFactory implements FallbackFactory<AddressServiceApi> {
    @Override
    public AddressServiceApi create(Throwable throwable) {
        return new AddressServiceApi(){

            @Override
            public RespMsg get(Integer id) {
                RespMsg respMsg = new RespMsg();
                respMsg.setCode(1);
                respMsg.setMsg("hystrix");
                return respMsg;
            }
        };
    }
}
