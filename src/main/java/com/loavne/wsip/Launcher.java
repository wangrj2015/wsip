package com.loavne.wsip;

import com.loavne.wsip.server.SipProxyServer;

/**
 * Created by wangrenjie on 17/3/21.
 */
public class Launcher {

    public static void main(String[] args){
        SipProxyServer.getInstance().start();
    }
}
