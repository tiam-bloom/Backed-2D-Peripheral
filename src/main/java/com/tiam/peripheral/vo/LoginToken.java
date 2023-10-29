package com.tiam.peripheral.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Tiam
 * @date 2023/10/23 16:43
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LoginToken extends Token {
    private String username;
    private String role;

    public LoginToken(Token token){
        super(token.getAccessToken(), token.getRefreshToken(), token.getExpire());
    }
}
