package com.clzmall.admin.mapper;

import com.clzmall.common.model.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/7.
 */
@Repository
public interface BannerMapper {

    List<Banner> selectAll();

    List<Banner> selectByShopId(@Param("shopId") Integer shopId, @Param("title") String title);

    int insert(@Param("vo") Banner banner);

}
