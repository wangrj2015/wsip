package com.loavne.wsip;

import com.loavne.wsip.server.SipServer;

/**
 * Created by wangrenjie on 17/3/21.
 */
public class Launcher {

    public static void main(String[] args){
        SipServer.getInstance().start();
    }
}
