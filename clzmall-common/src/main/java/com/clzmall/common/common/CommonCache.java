package com.clzmall.common.common;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by bairong on 2018/6/4.
 */
@Repository
public class CommonCache {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 存储key-value
     *
     * @param key
     * @param value
     */
    public void putValue(String key, String value) {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(key, value);
    }

    /**
     * 值增加1
     *
     * @param key
     */
    public void incValue(String key) {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.increment(key, 1);
    }

    /**
     * 存储 key-value，带超时时间
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void putValue(String key, String value, long timeout) {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * @param values
     * @throws Exception
     */
    public void putValue(Map<String, String> values) throws Exception {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.multiSet(values);
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        String result = valueOps.get(key);
        return StringUtils.isEmpty(result) ? null : result;
    }

    /**
     * 根据通配key,获取多个value
     *
     * @param pattern
     * @return
     */
    public List<String> getValueList(String pattern) {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        List<String> values = valueOps.multiGet(stringRedisTemplate.keys(pattern));
        return CollectionUtils.isEmpty(values) ? Lists.newArrayList() : values;
    }


    /**
     * 删除key
     *
     * @param key
     * @throws Exception
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }


    /**
     * 存储key-value
     *
     * @param key
     * @param value
     * @throws Exception
     */
    public Long lpush(String key, String value) {
        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        return listOps.leftPush(key, value);
    }

    /**
     * 从头部移除一个元素
     *
     * @param key
     * @throws Exception
     */
    public void lpop(String key) throws Exception {
        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        listOps.leftPop(key);
    }

    /**
     * 从末尾移除一个元素
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String rpop(String key) {

        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        return listOps.rightPop(key);
    }

    /**
     * 获取list的大小
     *
     * @param key
     * @return
     */
    public Long listSize(String key) {

        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        return listOps.size(key);
    }

    /**
     * opsForHash hmsetAll
     *
     * @param key
     * @param map
     */
    public void hmsetAll(String key, Map<String, String> map) {
        HashOperations<String, String, String> mapOps = stringRedisTemplate.opsForHash();
        mapOps.putAll(key, map);
    }

    /**
     * opsForHash hmget
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String hmget(String key, String hashKey) {
        HashOperations<String, String, String> mapOps = stringRedisTemplate.opsForHash();
        return mapOps.get(key, hashKey);

    }

    /**
     * opsForHash hmset
     *
     * @param h
     * @param hk
     * @param hv
     */
    public void hmset(String h, String hk, String hv) {
        HashOperations<String, String, String> mapOps = stringRedisTemplate.opsForHash();
        mapOps.put(h, hk, hv);
    }

    /**
     * 删除 hash
     * @param h
     * @param hk
     */
    public Long hdel(String h, String hk) {
        HashOperations<String, String, String> mapOps = stringRedisTemplate.opsForHash();
        return mapOps.delete(h, hk);
    }

//    public Set<ZSetOperations.TypedTuple<String>> zrangeByScoreWithScores(String key, double min, double max, int offset,
//                                                                          int count) {
//        ZSetOperations<String, String>         zset = stringRedisTemplate.opsForZSet();
//        Set<ZSetOperations.TypedTuple<String>> set  = zset.rangeByScoreWithScores(key, min, max, offset, count);
//        return set;
//    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
        Set<String> set = zset.rangeByScore(key, min, max);
        return set;
    }

    public boolean zadd(String key, String itemKey, double score) {
        ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
        return zset.add(key, itemKey, score);
    }

    public Long zrem(String key, String itemKey) {
        ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
        return zset.remove(key, itemKey);
    }

    /**
     * 获取过期时间
     *
     * @param key
     */
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
