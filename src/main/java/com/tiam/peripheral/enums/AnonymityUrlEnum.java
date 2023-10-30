package com.tiam.peripheral.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tiam
 * @date 2023/10/23 16:28
 * @description
 */
@AllArgsConstructor
@Getter
public enum AnonymityUrlEnum {
    /**
     * 无需登录即可访问的url
     */
    LOGIN("/login"),
    REGISTER("/register"),
    UN_LOGIN("/unLogin");


    private final String url;

    public static boolean isAnonymityUrl(String url) {
        // 遍历
        for (AnonymityUrlEnum value : AnonymityUrlEnum.values()) {
            if (value.url.equals(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取匿名访问路径
     *
     * @return 匿名访问路径列表
     */
    public static List<String> anonymityUrlList() {
        return Arrays.stream(AnonymityUrlEnum.values()).map(AnonymityUrlEnum::getUrl).collect(Collectors.toList());
    }
}
