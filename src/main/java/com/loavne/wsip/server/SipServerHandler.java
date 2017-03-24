package com.loavne.wsip.server;

import com.loavne.wsip.worker.WorkerFactory;
import com.loavne.wsip.protocol.SipMsgBuilder;
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
        String message = (String)msg;
        logger.debug(message);

        SipRequestMsg sipRequestMsg = (SipRequestMsg) SipMsgBuilder.build(message);
        WorkerFactory.getWorker(sipRequestMsg).work(ctx,sipRequestMsg);
        ctx.close();
    }
}
