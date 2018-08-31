package com.clzmall.admin.mapper;

import com.clzmall.common.model.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangjianshi on 18/8/5.
 */
@Repository
public interface OrdersMapper {

    Orders selectByPrimaryKey(Integer id);

    List<Orders> selectByUidAndStatus(@Param("uid") Integer uid, @Param("status") Integer status);

    int insert(@Param("vo") Orders vo);

    int updateSelective(@Param("vo") Orders vo);

}
