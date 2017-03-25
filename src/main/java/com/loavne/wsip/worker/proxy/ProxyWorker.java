package com.loavne.wsip.worker.proxy;

import com.loavne.wsip.worker.IWorker;
import com.loavne.wsip.protocol.SipRequestMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wangrenjie on 17/3/24.
 * 代理服务器
 */
public class ProxyWorker implements IWorker {

    public void work(ChannelHandlerContext ctx, SipRequestMsg msg) {

    }
}
