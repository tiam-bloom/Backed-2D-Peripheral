package com.tiam.peripheral.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tiam
 * @date 2023/10/23 16:28
 * @description
 */
@AllArgsConstructor
@Getter
public enum AnonymityUrl {
    /**
     * 无需登录即可访问的url
     */
    LOGIN("/login"),
    REGISTER("/register"),
    UN_LOGIN("/unLogin");


    private final String url;

    public static boolean isAnonymityUrl(String url) {
        // 遍历
        for (AnonymityUrl value : AnonymityUrl.values()) {
            if (value.url.equals(url)) {
                return true;
            }
        }
        return false;
    }
}
