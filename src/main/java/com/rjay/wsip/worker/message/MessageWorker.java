package com.rjay.wsip.worker.message;

import com.rjay.wsip.protocol.msg.SipRequestMsg;
import com.rjay.wsip.protocol.msg.SipStatusMsg;
import com.rjay.wsip.protocol.header.HeaderKeys;
import com.rjay.wsip.worker.AbstractWorker;
import com.rjay.wsip.worker.registrar.contact.ContactContext;
import com.rjay.wsip.worker.registrar.contact.ContactHolder;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangrenjie on 17/3/26.
 */
public class MessageWorker extends AbstractWorker{

    private Logger logger = LoggerFactory.getLogger(MessageWorker.class);

    @Override
    public void innerWork(final ChannelHandlerContext ctx, final SipRequestMsg msg) {
        String body = msg.getBody();
        if(null == body || body.contains("<?xml")){
            return;
        }
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
            logger.error("contact:{} not found ",contact);
            return;
        }
        contactContext.getChannel().writeAndFlush(msg);
    }
}
