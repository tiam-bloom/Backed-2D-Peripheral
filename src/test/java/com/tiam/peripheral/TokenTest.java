package com.tiam.peripheral;

import com.tiam.peripheral.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import org.junit.jupiter.api.Test;

/**
 * @author Tiam
 * @date 2023/10/29 10:51
 * @description
 */
public class TokenTest {

    @Test
    public void test(){
        String accessToken = TokenUtil.genAccessToken("admin", "admin");
        System.out.println(accessToken);

        Jws<Claims> claimsJws = TokenUtil.parseClaim(accessToken);
        JwsHeader header = claimsJws.getHeader();
        Claims payload = claimsJws.getPayload();
        System.out.println(header);
        System.out.println(payload);
        System.out.println(payload.get("username"));
    }
}
