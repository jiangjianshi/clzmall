package com.clzmall.app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clzmall.app.entity.dto.TemplateMsgDto;
import com.clzmall.common.common.WxConsts;
import com.clzmall.common.util.HttpUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by jiangjianshi on 18/9/2.
 */
@Slf4j
public class WxUtil {

    public static boolean sendTemplateMsg(TemplateMsgDto msgDto) {

        String accessToken = getAccessTokenForWX();
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
        try {
            String result = HttpUtil.post(url, JSON.toJSONString(msgDto));
            log.info("result:" + result);
            JSONObject json = JSON.parseObject(result);
            if ("ok".equals(json.get("errmsg"))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getAccessTokenForWX() {

        Map<String, String> param = Maps.newHashMap();
        param.put("appid", WxConsts.APPID);
        param.put("secret", WxConsts.SECRET);
        param.put("grant_type", WxConsts.WX_GRANT_TYPE_FOR_ACCESS_TOKEN);

        String url = "https://api.weixin.qq.com/cgi-bin/token";
        try {
            String result = HttpUtil.get(url, param);
            log.info("result:" + result);
            Map<String, String> resultMap = JSON.parseObject(result, Map.class);
            if (resultMap != null && !resultMap.isEmpty()) {
                return resultMap.get("access_token");
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
