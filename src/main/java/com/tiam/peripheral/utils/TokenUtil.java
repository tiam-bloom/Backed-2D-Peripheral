package com.tiam.peripheral.utils;

import com.tiam.peripheral.vo.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiPredicate;

/**
 * @author Tiam
 * @date 2023/10/23 16:38
 * @description
 */
public class TokenUtil {
    /**
     * 过期时间
     * 7小时
     * 7天
     */
    public static final int ACCESS_EXPIRE = 60 * 60 * 7;
    public static final int REFRESH_EXPIRE = 60 * 60 * 24 * 7;
    /**
     * 指定签名算法
     */
    public static final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个com.asiainfo.adapter.party.service.impl.CmxGroupAccountClientSVImpl, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */
    private final static String SECRET = "qwertyuidasdfghjklzxcvbnm122345678901";
    /**
     * 秘钥实例, 或者 Jwts.SIG.HS256.key().build() 随机密钥
     */
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    /**
     * jwt签发者
     */
    private final static String JWT_ISS = "Tiam";
    /**
     * jwt主题
     */
    private final static String SUBJECT = "Peripherals";


    public static Token genToken(String username, String role, String refreshToken) {
        return Token.builder()
                .accessToken(genAccessToken(username, role))
                .refreshToken(refreshToken)
                .expire(Instant.now().plusSeconds(ACCESS_EXPIRE).toEpochMilli())
                .build();
    }



    public static String genAccessToken(String username, String role) {
        // 令牌id
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));

        return Jwts.builder()
                // 设置头部信息
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                // 设置自定义负载信息payload
                .claim("username", username)
                .claim("role", role)
                // 令牌ID
                .id(uuid)
                // 过期日期
                .expiration(exprireDate)
                // 签发时间
                .issuedAt(new Date())
                // 主题
                .subject(SUBJECT)
                // 签发者
                .issuer(JWT_ISS)
                // 签名
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }

    /**
     * 解析token,
     * 使用不一致签名的私钥解析token会抛出异常
     * token过期时会抛出异常
     * 注意捕获处理
     *
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token);
    }

    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    public static Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }

    /**
     * 验证token: 解析传入的token获得username与role,
     * 根据username与role生成token, 与传入的token比较是否一致
     * 使用不一样私钥生成的token是不一致的, 所以私钥SECRET不能泄露
     *
     * @param token token
     * @return Predicate<String>
     */
    @Deprecated
    public static BiPredicate<String, String> verifyToken(String token) {
        return (username, role) -> StringUtils.equals(genAccessToken(username, role), token);
    }

    public static String genAccessToken(Map<String, Object> headers, Map<String, ?> claims) {
        return Jwts.builder()
                .header()
                .add(headers)
                .and()
                .claims(claims)
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }
}
