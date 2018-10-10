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

    int updateScoreByUid(@Param("score") int score, @Param("uid") Integer uid);

    int updateSelective(@Param("vo") WxUser vo);

}
