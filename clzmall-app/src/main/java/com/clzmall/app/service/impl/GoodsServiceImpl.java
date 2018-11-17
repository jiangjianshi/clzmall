package com.clzmall.app.service.impl;

import com.clzmall.app.entity.dto.GoodsDetailDto;
import com.clzmall.app.entity.dto.GoodsDto;
import com.clzmall.app.entity.dto.PriceDto;
import com.clzmall.app.entity.dto.PropertiesDto;
import com.clzmall.app.mapper.*;
import com.clzmall.app.service.GoodsService;
import com.clzmall.common.model.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by jiangjianshi on 18/8/7.
 */

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsPicsMapper goodsPicsMapper;

    @Autowired
    private GoodsPropertyMapper goodsPropertyMapper;

    @Autowired
    private GoodsPropertyTypeMapper goodsPropertyTypeMapper;

    @Override
    public List<GoodsCategory> listAllCat() {
        return goodsCategoryMapper.selectAll();
    }


    @Override
    public List<Banner> listBanner() {
        List<Banner> list = bannerMapper.selectAll();
        list.stream().forEach(x -> {
            x.setBackgroundImgUrl(StringUtils.isNotEmpty(x.getBackgroundImgUrl()) ? x.getBackgroundImgUrl().trim() + "?x-oss-process=image/resize,h_500" : "");
        });
        return list;
    }


    @Override
    public List<GoodsDto> listGoods(Integer catId, String name) {
        List<GoodsDto> list = goodsMapper.selectByCategoryAndName(catId, name);
        list.stream().forEach(x -> {
            x.setPicUrl(StringUtils.isNotEmpty(x.getPicUrl()) ? x.getPicUrl().trim() + "?x-oss-process=image/resize,h_240" : "");
        });
        return list;
    }

    @Override
    public GoodsDetailDto getGoodsDetail(Integer goodsId) {


        Goods baseInfo = goodsMapper.selectByPrimaryKey(goodsId);
        List<GoodsPics> pics = goodsPicsMapper.selectByGoodsId(goodsId);

        List<GoodsProperty> propertiesList = goodsPropertyMapper.selectByGoodsId(goodsId);
        Map<Integer, List<GoodsProperty>> groupMap = propertiesList.stream().collect(groupingBy(GoodsProperty::getPropTypeId));

        List<PropertiesDto> properties = Lists.newArrayList();
        for (Integer typeId : groupMap.keySet()) {
            GoodsPropertyType propType = goodsPropertyTypeMapper.selectByPrimaryKey(typeId);

            PropertiesDto dto = new PropertiesDto();
            BeanUtils.copyProperties(propType, dto);

            dto.setChildsCurGoods(groupMap.get(typeId));
            properties.add(dto);
        }

        GoodsDetailDto detailDto = new GoodsDetailDto();

        GoodsDto dto = new GoodsDto();
        BeanUtils.copyProperties(baseInfo, dto);
        for (GoodsPics pic : pics) {
            if (pic.getIsDefault() == 1) {
                dto.setPicUrl(pic.getPicUrl());
                dto.setSmallPicUrl(StringUtils.isNotEmpty(pic.getPicUrl()) ? pic.getPicUrl().trim() + "?x-oss-process=image/resize,h_500" : "");
            }
        }
        detailDto.setBasicInfo(dto);
        detailDto.setPics(pics);
        detailDto.setProperties(properties);
        return detailDto;
    }


    @Override
    public PriceDto calSelectedPrice(Integer goodsId, String propertyChildIds) {

        Goods gd = goodsMapper.selectByPrimaryKey(goodsId);
        Map<String, String> map = Splitter.on(",").omitEmptyStrings().trimResults().withKeyValueSeparator(":").split(propertyChildIds);
        Collection<String> values = map.values();

        double selectedPrice = gd.getMinPrice();
        for (String id : values) {
            GoodsProperty prop = goodsPropertyMapper.selectByPrimaryKey(Integer.parseInt(id));
            selectedPrice += prop.getAddedPrice();
        }

        PriceDto price = new PriceDto();
        price.setPrice(selectedPrice);
        price.setScore(0);
        price.setStores(gd.getStoreAmount());
        return price;
    }
}
