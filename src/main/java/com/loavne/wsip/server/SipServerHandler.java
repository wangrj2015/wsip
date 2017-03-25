package com.loavne.wsip.server;

import com.loavne.wsip.worker.WorkerFactory;
import com.loavne.wsip.protocol.SipRequestMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangrenjie on 17/3/21.
 */
public class SipServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(SipServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SipRequestMsg sipRequestMsg = (SipRequestMsg) msg;
        WorkerFactory.getWorker(sipRequestMsg).work(ctx,sipRequestMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("exceptionCaught",cause);
        ctx.close();
    }
}
