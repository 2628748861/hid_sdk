package com.wangxiaobao.sdk.hid.utils;

public class HexUtil {

    private static final char HexCharArr[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    private static final String HexStr = "0123456789abcdef";

    public static byte[] getPlayLoad(byte[] data){
        byte[] playLoad = new byte[data.length+1];
        byte[] b=HexUtil.int2byte(data.length);
        System.arraycopy(b,0,playLoad,0,b.length);
        System.arraycopy(data,0,playLoad,1,data.length);
        return playLoad;
    }

    public static byte[] int2byte(int res) {
        byte[] targets = new byte[2];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        return targets;
    }

    public static String getHexLength(byte[] bytes) {
        String hexLen = Integer.toHexString(bytes.length);
        //奇数前补0
        hexLen = (hexLen.length() & 1) == 1 ? "0" + hexLen : hexLen;
        return hexLen;
    }


    public static byte[] hexToByteArr(String hexStr) {
        char[] charArr = hexStr.toCharArray();
        byte btArr[] = new byte[charArr.length / 2];
        int index = 0;
        for (int i = 0; i < charArr.length; i++) {
            int highBit = HexStr.indexOf(charArr[i]);
            int lowBit = HexStr.indexOf(charArr[++i]);
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**16进制字符串转换ascill码
     * @param hex
     * @return
     */
    public static String hexStringToAscill(String hex){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            String str = hex.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        return output.toString();
    }
    /**将字符串转位16进制字符串
     * @param value
     * @return
     */
    public static String stringToAsciiOfHex(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            sbu.append(Integer.toHexString(chars[i]));
        }
        return sbu.toString();
    }

    /**字节数组转16进制字符串
     * @param btArr 字节数组
     * @return
     */
    public static String byteArrToHex(byte[] btArr) {
        char strArr[] = new char[btArr.length * 2];
        int i = 0;
        for (byte bt : btArr) {
            strArr[i++] = HexCharArr[bt>>>4 & 0xf];
            strArr[i++] = HexCharArr[bt & 0xf];
        }
        return new String(strArr);
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        return sb.toString();
    }

    /**字符串转16进制字符串
     * @param str 字符串
     * @return
     */
    public static String StringToHex(String str){
        return byteArrToHex(str.getBytes());
    }

    public static String hexToString(byte[] value) {
        StringBuilder builder = new StringBuilder();
        for (byte item: value){
            String hex = Integer.toHexString(item & 0x000000ff);
            if (hex.length() == 1) {
                builder.append("0");
            }
            builder.append(hex);
        }
        return builder.toString();
    }

    public static int bytes2int(byte[] src){
        int firstByte = 0;
        int secondByte = 0;
        int thirdByte = 0;
        int fourthByte = 0;
        int index = 0;
        int anUnsignedInt = 0;

        firstByte = (0x000000FF & ((int) src[index]));
        secondByte = (0x000000FF & ((int) src[index+1]));
        thirdByte = (0x000000FF & ((int) src[index+2]));
        fourthByte = (0x000000FF & ((int) src[index+3]));

        anUnsignedInt  = (((int) (firstByte<<24|secondByte<<16|thirdByte<<8|fourthByte)) & 0x00000000FFFFFFFF);
        return anUnsignedInt  ;
    }





}
