package com.hmdp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *
 * redis 实现全局唯一id
 * @author Hongbin BAO
 * @Date 2023/12/16 16:35
 */
@Component
public class RedisIdWorker {

    // 开始时间戳
    private static final long BEGIN_TIMESTAMP = 1640995200;
    // 序列号位数
    private static final int COUNT_BITS = 32;

    private StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 业务前缀
    public long nextId(String keyPrefix){
        // 1. 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond -BEGIN_TIMESTAMP;

        // 2. 生成序列号
        // 2.1 获取当前日期精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2 自增长
        Long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
        // 3. 拼接并返回
        return timestamp << COUNT_BITS | count;
    }

//    public static void main(String[] args){
//        LocalDateTime time = LocalDateTime.of(2022,1,1,0,0,0);
//
//        long second = time.toEpochSecond(ZoneOffset.UTC);
//
//        System.out.println("second = " + second);
//    }
}
