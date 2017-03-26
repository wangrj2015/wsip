package com.loavne.wsip;

import com.loavne.wsip.util.Md5;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class TestDigest {

    public static void main(String[] args) throws Exception{
        //H(H(username:realm:password)：nonce：cnonce：H(requestMothod:request-URI))

        //876b5481c9474bad25fc2d676ae72efe
        String response = Md5.encode(Md5.encode("001:127.0.0.1:001") + ":1490450481444:" + Md5.encode("REGISTER:sip:localhost:10003;transport=tcp"));
        System.out.println(response);
    }
}
