package com.clzmall.common.util.excel;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by bairong on 2018/11/11.
 */
public class MakeSql {

    public static void main(String[] args) {


        ImportExcelUtil<FreeInterestRedPacketTaskPO> importExcelUtil = new ImportExcelUtil<>();
        importExcelUtil.read("/Users/bairong/Desktop/jiaju.xlsx", FreeInterestRedPacketTaskPO.class, 0);
        List<FreeInterestRedPacketTaskPO> freeInterestRedPacketTaskPOList = importExcelUtil.getData();
        System.out.println(freeInterestRedPacketTaskPOList.toString());
        int id =1;
//        for (FreeInterestRedPacketTaskPO po : freeInterestRedPacketTaskPOList) {
//            if (StringUtils.isEmpty(po.getRemark()))
//                continue;
//            StringBuffer sql = new StringBuffer("insert into goods (id, name, min_price, status, create_time, update_time) values (");
//            sql.append("\'").append(id).append("\',")
//                    .append("\'").append(po.getName()).append("\',")
//                    .append("\'").append(po.getPrice()).append("\',")
//                    .append("\'").append(1).append("\',")
//                    .append("now(),").append("now());");
//            System.out.println(sql.toString());
//            id++;
//        }

//        for (FreeInterestRedPacketTaskPO po : freeInterestRedPacketTaskPOList) {
//            if(StringUtils.isEmpty(po.getRemark()))
//                continue;
//            StringBuffer sql = new StringBuffer("insert into goods_properties (goods_id, prop_type_id, prop_value, added_price, added_amount, create_time, update_time) values (");
//            sql.append("\'").append(id).append("\',")
//                    .append("\'").append(1).append("\',")
//                    .append("\'").append(po.getCaizhi()).append("\',")
//                    .append("\'").append(0).append("\',")
//                    .append("\'").append(0).append("\',")
//                    .append("now(),").append("now());");
//            System.out.println(sql.toString());
//
//            StringBuffer sql2 = new StringBuffer("insert into goods_properties (goods_id, prop_type_id, prop_value, added_price, added_amount, create_time, update_time) values (");
//            sql2.append("\'").append(id).append("\',")
//                    .append("\'").append(2).append("\',")
//                    .append("\'").append(po.getSize()).append("\',")
//                    .append("\'").append(0).append("\',")
//                    .append("\'").append(0).append("\',")
//                    .append("now(),").append("now());");
//            System.out.println(sql2.toString());
//            id++;
//        }

        for (FreeInterestRedPacketTaskPO po : freeInterestRedPacketTaskPOList) {
            if(StringUtils.isEmpty(po.getRemark()))
                continue;
            StringBuffer sql = new StringBuffer("insert into goods_pics (goods_id, pic_url, is_default, status, create_time, update_time) values (");
            sql.append("\'").append(id).append("\',")
                    .append("\'").append(po.getRemark()).append("\',")
                    .append("\'").append(1).append("\',")
                    .append("\'").append(1).append("\',")
                    .append("now(),").append("now());");
            System.out.println(sql.toString());
            id++;
        }
    }
}
