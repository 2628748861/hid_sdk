package com.wangxiaobao.sdk.hid.utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String formatMac(String mac, String split) {
        String regex = "[0-9a-fA-F]{12}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mac);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("mac format is error");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            char c = mac.charAt(i);
            sb.append(c);
            if ((i & 1) == 1 && i <= 9) {
                sb.append(split);
            }
        }

        return sb.toString();
    }
}
