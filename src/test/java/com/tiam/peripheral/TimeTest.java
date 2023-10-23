package com.tiam.peripheral;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Tiam
 * @date 2023/10/23 17:42
 * @description
 */
public class TimeTest {
    public static final int ACCESS_EXPIRE = 60;
    @Test
    public void test1(){
        long epochMilli = Instant.now().plusSeconds(ACCESS_EXPIRE).toEpochMilli();
        System.out.println(epochMilli);
        System.out.println(System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(epochMilli);
        System.out.println(timestamp);
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
