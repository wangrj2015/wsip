package com.rjay.wsip;

import com.rjay.wsip.server.SipServer;

/**
 * Created by wangrenjie on 17/3/21.
 */
public class Launcher {

    public static void main(String[] args){
        if(args.length > 0){
            Config.setPort(Integer.parseInt(args[0]));
        }
        SipServer.getInstance().start();
    }
}
