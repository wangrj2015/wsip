package com.rjay.wsip.worker.registrar;

import com.rjay.wsip.protocol.msg.SipStatusMsg;
import com.rjay.wsip.protocol.header.HeaderKeys;
import com.rjay.wsip.util.Constants;
import com.rjay.wsip.worker.AbstractWorker;
import com.rjay.wsip.protocol.msg.SipRequestMsg;
import com.rjay.wsip.worker.registrar.contact.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangrenjie on 17/3/24.
 * 注册服务器
 */
public class RegisterWorker extends AbstractWorker {

    private Logger logger = LoggerFactory.getLogger(RegisterWorker.class);

    @Override
    public void innerWork(ChannelHandlerContext ctx, SipRequestMsg msg) {
        String contact = msg.getContact();
        ContactContext contactContext = ContactHolder.getContactContext(contact);
        if(null != msg.getHeaders().get(HeaderKeys.KEY_AUTHENTICATION)){
            if(null == contactContext || !(contactContext.getContactState() instanceof ContactStateUnauthorized)){
                contactContext = rebuildContext(contact,new ContactStateUnauthorized());
            }
        }else{
            contactContext = rebuildContext(contact,new ContactStateInit());
        }
        contactContext.setChannel(ctx.channel());

        SipStatusMsg statusMsg = contactContext.handle(msg);
        if(null == statusMsg){
            return;
        }
        final ContactContext c = contactContext;
        ChannelFuture f = ctx.writeAndFlush(statusMsg);
        f.addListener(channelFuture -> c.nextState());
    }

    private ContactContext rebuildContext(String contact, ContactState contactState){
        ContactContext contactContext = new ContactContext(contact);
        contactContext.setExpires(Constants.DEFAULT_EXPIRES);
        contactContext.setLastActiveMillTime(System.currentTimeMillis());
        contactContext.setContactState(contactState);
        ContactHolder.putContactContext(contact,contactContext);
        return contactContext;
    }

}
