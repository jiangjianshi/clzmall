package com.clzmall.common.util.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clzmall.common.util.HttpUtil;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by bairong on 2018/11/11.
 */
public class MakeSql {

    public static void main(String[] args) {


        ImportExcelUtil<FreeInterestRedPacketTaskPO> importExcelUtil = new ImportExcelUtil<>();
        importExcelUtil.read("/Users/bairong/Desktop/user_record_2019-02-21.xls", FreeInterestRedPacketTaskPO.class, 0);
        List<FreeInterestRedPacketTaskPO> freeInterestRedPacketTaskPOList = importExcelUtil.getData();

        List lines = Lists.newArrayList();
        for (FreeInterestRedPacketTaskPO row : freeInterestRedPacketTaskPOList) {
            String uid = row.getUserCode().split("-")[1];
            String clickProduct = row.getClickProductName();
            String url = "";
            boolean containFlag = false;
            try {
                String json = HttpUtil.get(url);
//                System.out.println(json);
                JSONObject obj = JSONObject.parseObject(json);
                JSONArray array = obj.getJSONArray("productList");
//                System.out.println(JSON.toJSON(array));
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = (JSONObject) array.get(i);
                    String pName = jsonObject.getString("name");
                    if (clickProduct.equals(pName)) {
                        containFlag = true;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String result = "";
            if (containFlag) {
                result = "uid=" + uid + " result=" + containFlag;
            } else {
                result = "uid=" + uid + " result=" + containFlag+".........";
            }
            lines.add(result);

        }
        try {
            FileUtils.writeLines(new File("/Users/bairong/Desktop/check_result.txt"), lines, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
