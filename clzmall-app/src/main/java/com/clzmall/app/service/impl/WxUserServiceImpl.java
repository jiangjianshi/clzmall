package com.clzmall.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clzmall.app.entity.dto.WxSession;
import com.clzmall.app.service.WxUserService;
import com.clzmall.app.mapper.WxUserMapper;
import com.clzmall.common.common.WxConsts;
import com.clzmall.common.model.WxUser;
import com.clzmall.common.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bairong on 2018/7/29.
 */
@Slf4j
@Service
@Transactional
public class WxUserServiceImpl implements WxUserService {

    @Resource
    private WxUserMapper wxUserMapper;


    @Override
    public int saveUser(Integer inviterUid, String code, WxUser user) {

//        https://api.weixin.qq.com/sns/jscode2session?appid=wx20452b87603c728d&secret=e5e1887306ee8ea0aad9fd6f174d434a&js_code=071FQsy60RS7sJ1Du7y60d5By60FQsy-&grant_type=authorization_code

        Map<String, String> param = new HashMap<>();
        param.put("appid", WxConsts.APPID);
        param.put("secret", WxConsts.SECRET);
        param.put("js_code", code);
        param.put("grant_type", WxConsts.WX_GRANT_TYPE_FOR_OPENID);

        WxSession session = null;
        try {
            String result = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", param);
            session = JSON.parseObject(result, WxSession.class);

            WxUser wxUser = wxUserMapper.selectByOpenId(session.getOpenid());
            if (wxUser != null) {
                return wxUser.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("用户注册异常");
        }
        if (session != null) {
            user.setOpenId(session.getOpenid());
            user.setToken(session.getSession_key());
            user.setScore(5);
        }
        if (inviterUid != null) {
            user.setInviterUid(inviterUid);
            int count = wxUserMapper.updateScoreByUid(1, inviterUid);
            log.info("更新数量：{}", count);
        }
        wxUserMapper.insert(user);
        return user.getId();
    }


    @Override
    public WxUser checkLogin(String uid) {

        return wxUserMapper.selectByPrimaryKey(Integer.parseInt(uid));
    }


    @Override
    public Integer getUserScore(String uid) {

        WxUser user = wxUserMapper.selectByPrimaryKey(Integer.parseInt(uid));
        if (user != null) {
            return user.getScore();
        } else {
            return 0;
        }
    }

    @Override
    public WxUser getUserInfo(String uid) {


        WxUser user = wxUserMapper.selectByPrimaryKey(Integer.parseInt(uid));
        return user;
    }


    @Override
    public boolean bindMobile(Integer uid, String encData, String iv) {

        byte[] encrypData = Base64.decodeBase64(encData.getBytes());
        byte[] ivData = Base64.decodeBase64(iv.getBytes());
        WxUser wxUser = wxUserMapper.selectByPrimaryKey(uid);
        byte[] wxSessionKeyData = Base64.decodeBase64(wxUser.getToken().getBytes());
        try {
            String resultJson = decrypt(wxSessionKeyData, ivData, encrypData);
            log.info("获取手机号解密结果:{}", resultJson);
            Map<String, String> resultMap = JSONObject.parseObject(resultJson, Map.class);
            String userMobile = resultMap.get("phoneNumber");

            WxUser user = new WxUser();
            user.setId(uid);
            user.setMobile(userMobile);
            int cnt = wxUserMapper.updateSelective(user);
            log.info("更新手机号：{}", cnt);
        } catch (Exception e) {
            log.error("获取手机号失败，{}", e);
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encData), "UTF-8");
    }
}
