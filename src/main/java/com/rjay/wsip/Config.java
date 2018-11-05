package com.rjay.wsip;

/**
 * Created by wangrenjie on 17/12/14.
 */
public class Config {

    /**
     * 端口
     */
    private static int port  = 10003;

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Config.port = port;
    }
}
