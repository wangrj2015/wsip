package com.rjay.wsip.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class Md5 {

    public static String encode(String s) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(s.getBytes());
        byte[] b = md.digest();

        for(int offset = 0; offset < b.length; ++offset) {
            int i = b[offset];
            if(i < 0) {
                i += 256;
            }

            if(i < 16) {
                sb.append("0");
            }

            sb.append(Integer.toHexString(i));
        }

        return sb.toString();
    }
}
