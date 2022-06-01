package com.sdms.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5单向加密工具
 */
public class MD5Utils {

    /**
     * MD5不加盐算法
     *
     * @param str 要加密的字符串
     * @return 加密得到的MD5序列
     */
    public static String encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte b : byteDigest) {
                i = b;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5加盐算法(Shiro实现)
     *
     * @param str   要加密的字符串
     * @param salt  盐
     * @param count 加密次数
     * @return 加密得到的MD5序列
     */
    public static String encodeWithSalt(String str, String salt, int count) {
        final SimpleHash hash = new SimpleHash("MD5", str, salt, count);
        return hash.toString();
    }
}
