package com.tiam.peripheral;

import com.tiam.peripheral.utils.TokenUtil;
import org.junit.jupiter.api.Test;

/**
 * @author Tiam
 * @date 2023/10/29 10:51
 * @description
 */
public class TokenTest {

    @Test
    public void test(){
        String accessToken = TokenUtil.genAccessToken("admin");
        System.out.println(accessToken);
    }
}
