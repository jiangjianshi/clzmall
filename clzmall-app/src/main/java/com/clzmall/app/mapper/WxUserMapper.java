package com.clzmall.app.mapper;

import com.clzmall.common.model.WxUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangjianshi on 18/8/1.
 */
@Repository
public interface WxUserMapper {

    WxUser selectByPrimaryKey(Integer uid);


    WxUser selectByOpenId(String openId);

    int insert(@Param("vo") WxUser vo);


}
