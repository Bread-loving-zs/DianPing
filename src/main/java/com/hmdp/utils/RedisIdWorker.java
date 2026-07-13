package com.hmdp.utils;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RedisIdWorker {

    public Long nextId(String keyPrefix) {
        //1.生成时间戳
        long timestamp = System.currentTimeMillis() / 1000;
        //2.生成序列号
        long sequence = 1;

        //3.拼接返回结果
        return timestamp << 22 | sequence;
    }

}
