package com.loavne.wsip.server;

import com.loavne.wsip.protocol.msg.SipErrorMsg;
import com.loavne.wsip.protocol.msg.SipMsg;
import com.loavne.wsip.worker.WorkerFactory;
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
        if(msg instanceof SipErrorMsg){
            return;
        }
        SipMsg sipMsg = (SipMsg) msg;
        WorkerFactory.getWorker(sipMsg).work(ctx,sipMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("channel:{}",ctx.channel().remoteAddress(),cause);
        ctx.close();
    }
}
