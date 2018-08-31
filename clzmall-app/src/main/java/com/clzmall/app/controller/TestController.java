package com.clzmall.app.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bairong on 2018/8/6.
 */

@RestController
@RequestMapping("/{d}/orders/")
public class TestController {

    @RequestMapping("getDomain")
    public String listOrders(@PathVariable("d") String dom, HttpServletRequest req) {

        return dom;
    }
}
