package com.loavne.wsip.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wangrenjie on 17/3/21.
 */
public class SipProxyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String out = (String)msg;
        System.out.println(out);
    }
}
