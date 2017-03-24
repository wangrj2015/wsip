package com.loavne.wsip.worker;

import com.loavne.wsip.protocol.SipRequestMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wangrenjie on 17/3/24.
 */
public interface IWorker {

    void work(ChannelHandlerContext context, SipRequestMsg msg);
}
