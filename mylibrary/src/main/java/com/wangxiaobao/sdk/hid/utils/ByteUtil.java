package com.wangxiaobao.sdk.hid.utils;

public class ByteUtil {


    public static String strTo16(String str){
        StringBuilder sb=new StringBuilder();
        for(char c: str.toCharArray()){
            sb.append(Integer.toHexString(c));
        }
        return sb.toString();
    }

    public static String hexStringToString(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


}
