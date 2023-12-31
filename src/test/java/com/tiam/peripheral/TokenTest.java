package com.tiam.peripheral;

import com.tiam.peripheral.exception.BizException;
import com.tiam.peripheral.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Tiam
 * @date 2023/10/29 10:51
 * @description
 */
public class TokenTest {

    @Test
    public void test() {
        String accessToken = TokenUtil.genAccessToken("admin", "admin");
        System.out.println(accessToken);

        Jws<Claims> claimsJws = TokenUtil.parseClaim(accessToken);
        JwsHeader header = claimsJws.getHeader();
        Claims payload = claimsJws.getPayload();
        System.out.println(header);
        System.out.println(payload);
        System.out.println(payload.get("username"));
    }

    /**
     * 测试解析失败时
     */
    @Test
    public void test1() {
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicm9sZSI6ImFkbWluIiwianRpIjoiNDM5YWRjYzgtNTk0MS00MGNiLWIwY2UtMmQyNWZlMzVhMmZjIiwiZXhwIjoxNjk4NjM3Mzg1LCJpYXQiOjE2OTg2MzczMjUsInN1YiI6IlBlcmlwaGVyYWxzIiwiaXNzIjoiVGlhbSJ9.wCrCsMDuVGrzyasd3jXJX1NaBucYT7weKtTBU6NEKjI";
        Claims claims = TokenUtil.parsePayload("acce.ssT.oken");  // 不合法时抛出异常
//        Claims claims = TokenUtil.parsePayload(accessToken);

        String name = claims.get("username1", String.class);  // 不存在时取null
        String role = claims.get("role", String.class);
        System.out.println(name);
        System.out.println(role);
    }

    /**
     * 对比token 页眉.有效载荷.签名
     */
    @Test
    public void test2() throws InterruptedException {
        String username = "admin";
        String role = "admin";
//        String accessToken = TokenUtil.genAccessToken(username, role);
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicm9sZSI6ImFkbWluIiwianRpIjoiNDM5YWRjYzgtNTk0MS00MGNiLWIwY2UtMmQyNWZlMzVhMmZjIiwiZXhwIjoxNjk4NjM3Mzg1LCJpYXQiOjE2OTg2MzczMjUsInN1YiI6IlBlcmlwaGVyYWxzIiwiaXNzIjoiVGlhbSJ9.wCrCsMDuVGrzyasd3jXJX1NaBucYT7weKtTBU6NEKjI";

        // 生成不同签发日期的token
        Thread.sleep(3000);
//        String accessToken1 = TokenUtil.genAccessToken(username, role);

        Claims claims = null;
        try {
            claims = TokenUtil.parsePayload(accessToken);
        } catch (Exception e) {
            throw new BizException("accessToken不合法," + e.getMessage());
        }
        JwsHeader jwsHeader = TokenUtil.parseHeader(accessToken);
        String accessToken1 = TokenUtil.genAccessToken(jwsHeader, claims);


        String[] split = accessToken.split("\\.");
        String[] split1 = accessToken1.split("\\.");
        System.out.println(Arrays.toString(split));
        System.out.println(Arrays.toString(split1));
        System.out.println(StringUtils.equals(accessToken, accessToken1));
        System.out.println(StringUtils.equals(split[0], split1[0]));
        System.out.println(StringUtils.equals(split[1], split1[1]));
        System.out.println(StringUtils.equals(split[2], split1[2]));

    }

    @Test
    public void test4() throws InterruptedException {
        String accessToken = TokenUtil.genAccessToken("admin", "admin");
        System.out.println(accessToken);
        Thread.sleep(3000);
        // 测试是否能解析过期的token => 过期抛出 ExpiredJwtException异常
        Jws<Claims> claimsJws = TokenUtil.parseClaim(accessToken);
        Claims payload = claimsJws.getPayload();
        System.out.println(payload);

    }

    @Test
    public void test5() {
        // 随机生成的密钥
        SecretKey key = Jwts.SIG.HS256.key().build();
        String format = key.getFormat();
        System.out.println(new String(key.getEncoded()));
        // 自定义密钥
        SecretKey secretKey = Keys.hmacShaKeyFor("1231234567890awertyusdfghjertyudfghasd".getBytes());
        System.out.println(new String(secretKey.getEncoded(), StandardCharsets.UTF_8));


        String jws = Jwts.builder().subject("Joe").signWith(key).compact();
        System.out.println(jws);

        Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(jws);
        System.out.println(claimsJws);
    }
}
