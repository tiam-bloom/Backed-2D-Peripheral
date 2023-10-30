package com.tiam.peripheral.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Tiam
 * @date 2023/10/23 16:39
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
    private Long expire;
}
