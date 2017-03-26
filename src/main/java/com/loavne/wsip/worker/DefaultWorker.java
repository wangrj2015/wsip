package com.loavne.wsip.worker;

import com.loavne.wsip.protocol.msg.SipRequestMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class DefaultWorker implements IWorker{

    public void work(ChannelHandlerContext ctx, SipRequestMsg msg) {
        //do nothing
    }
}
