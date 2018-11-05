package com.rjay.wsip.worker;

import com.rjay.wsip.protocol.msg.SipMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wangrenjie on 17/3/24.
 */
public interface IWorker {

    void work(ChannelHandlerContext ctx, SipMsg msg);
}
