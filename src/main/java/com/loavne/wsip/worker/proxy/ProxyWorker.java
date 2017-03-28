package com.loavne.wsip.worker.proxy;

import com.loavne.wsip.protocol.header.HeaderKeys;
import com.loavne.wsip.protocol.msg.SipStatusMsg;
import com.loavne.wsip.worker.AbstractWorker;
import com.loavne.wsip.protocol.msg.SipRequestMsg;
import com.loavne.wsip.worker.registrar.contact.ContactContext;
import com.loavne.wsip.worker.registrar.contact.ContactHolder;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangrenjie on 17/3/24.
 * 代理服务器
 */
public class ProxyWorker extends AbstractWorker {

    private Logger logger = LoggerFactory.getLogger(ProxyWorker.class);

    @Override
    public void innerWork(ChannelHandlerContext ctx, SipRequestMsg msg) {
        String contact = msg.getContact();
        ContactContext contactContext = ContactHolder.getOkContactContext(contact);
        if(null == contactContext){
            logger.error("contact:{} not found",contact);
            return;
        }
        contactContext.getChannel().writeAndFlush(msg);
    }

    @Override
    public void innerWork(ChannelHandlerContext ctx, SipStatusMsg msg) {
        String from = msg.getHeaders().get(HeaderKeys.KEY_FROM);
        String contact = from.substring(from.indexOf("<") + 1, from.indexOf(">"));
        ContactContext contactContext = ContactHolder.getOkContactContext(contact);
        if(null == contactContext){
            logger.error("contact:{} not found",contact);
            return;
        }
        contactContext.getChannel().writeAndFlush(msg);

    }
}
