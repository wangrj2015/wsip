package com.rjay.wsip.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class Constants {

    public static final String SERVER = "wsip/1.0 (wangrenjie)";

    public static final int DEFAULT_EXPIRES = 1200;

    public static final String REALM = "realm";

    public static final String NONCE = "nonce";

    public static final String USERNAME = "username";

    public static final String URI = "uri";

    public static final String RESPONSE = "response";

    public static final List<String> KEYLIST = new ArrayList<String>();

    static{
        KEYLIST.add(REALM);
        KEYLIST.add(NONCE);
        KEYLIST.add(USERNAME);
        KEYLIST.add(URI);
        KEYLIST.add(RESPONSE);
    }
}
