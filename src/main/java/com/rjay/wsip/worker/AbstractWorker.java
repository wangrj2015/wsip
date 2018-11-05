package com.rjay.wsip.worker;

import com.rjay.wsip.protocol.msg.SipMsg;
import com.rjay.wsip.protocol.msg.SipRequestMsg;
import com.rjay.wsip.protocol.msg.SipStatusMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wangrenjie on 17/3/28.
 */
public abstract class AbstractWorker implements IWorker{

    public final void work(ChannelHandlerContext ctx, SipMsg msg) {
        if(msg instanceof SipRequestMsg){
            innerWork(ctx, (SipRequestMsg)msg);
        }else if(msg instanceof SipStatusMsg){
            innerWork(ctx, (SipStatusMsg)msg);
        }
    }

    public abstract void innerWork(ChannelHandlerContext ctx, SipRequestMsg sipRequestMsg);

    public void innerWork(ChannelHandlerContext ctx, SipStatusMsg sipStatusMsg){};
}
