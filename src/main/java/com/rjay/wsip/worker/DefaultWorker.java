package com.rjay.wsip.worker;

import com.rjay.wsip.protocol.msg.SipRequestMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class DefaultWorker extends AbstractWorker{

    @Override
    public void innerWork(ChannelHandlerContext ctx, SipRequestMsg sipRequestMsg) {
        //do nothing
    }
}
