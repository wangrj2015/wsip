package com.loavne.wsip.worker.registrar;

import com.loavne.wsip.protocol.SipStatusMsg;
import com.loavne.wsip.worker.IWorker;
import com.loavne.wsip.protocol.SipRequestMsg;
import com.loavne.wsip.worker.registrar.contact.ContactContext;
import com.loavne.wsip.worker.registrar.contact.ContactHolder;
import com.loavne.wsip.worker.registrar.contact.ContactStateInit;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangrenjie on 17/3/24.
 * 注册服务器
 */
public class RegisterWorker implements IWorker {

    private Logger logger = LoggerFactory.getLogger(RegisterWorker.class);

    private static final int DEFAULT_EXPIRES = 1200;

    public void work(ChannelHandlerContext ctx, SipRequestMsg msg) {
        String contact = msg.getContact();
        ContactContext contactContext = ContactHolder.getContactContext(contact);
        if(null == contactContext){
            contactContext = new ContactContext(contact);
            contactContext.setExpires(DEFAULT_EXPIRES);
            contactContext.setExpiresMillTimes(System.currentTimeMillis() + DEFAULT_EXPIRES*1000);
            contactContext.setContactState(new ContactStateInit());
            ContactHolder.putContactContext(contact,contactContext);
        }
        SipStatusMsg statusMsg = contactContext.handle(msg);

        final ContactContext c = contactContext;
        ChannelFuture f = ctx.writeAndFlush(statusMsg);
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                c.nextState();
            }
        });
    }

}
