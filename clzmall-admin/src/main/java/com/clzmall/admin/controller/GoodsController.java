package com.clzmall.admin.controller;

import com.clzmall.admin.service.GoodsService;
import com.clzmall.common.common.PagedList;
import com.clzmall.common.common.RespMsg;
import com.clzmall.common.model.GoodsCategory;
import com.clzmall.common.model.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by jiangjianshi on 18/9/1.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Resource
    private GoodsService goodsService;


    @RequestMapping(value = "/listCategory")
    public PagedList<GoodsCategory> listCategry(HttpServletRequest req) {

        PagedList<GoodsCategory> pageList = goodsService.listCategry(getUid(req));
        return pageList;
    }

    @RequestMapping(value = "/saveCategory")
    public RespMsg<Integer> saveCategry(HttpServletRequest req, GoodsCategory cat) {

        cat.setShopId(getUid(req));
        int count = goodsService.saveCategory(cat);
        return success("添加成功", count);
    }

}
