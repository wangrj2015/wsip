package com.loavne.wsip.worker.message;

import com.loavne.wsip.protocol.msg.SipRequestMsg;
import com.loavne.wsip.protocol.msg.SipStatusMsg;
import com.loavne.wsip.protocol.header.HeaderKeys;
import com.loavne.wsip.util.MapUtils;
import com.loavne.wsip.worker.AbstractWorker;
import com.loavne.wsip.worker.registrar.contact.ContactContext;
import com.loavne.wsip.worker.registrar.contact.ContactHolder;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

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
        ChannelFuture f = contactContext.getChannel().writeAndFlush(msg);
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                SipStatusMsg statusMsg = new SipStatusMsg(msg.getVersion(),"200","Ok");
                Map<String,String> headers = statusMsg.getHeaders();
                headers.put(HeaderKeys.KEY_CONTENT_LENGTH,"0");
                MapUtils.copy(msg.getHeaders(), headers, new MapUtils.ValueFilter() {
                            public String newValue(String key, String value) {
                                //To
                                if(HeaderKeys.KEY_TO.equals(key)){
                                    return "<" + value + ">;tag=" + UUID.randomUUID().toString().replaceAll("-","");
                                }
                                return value;
                            }
                        }, HeaderKeys.KEY_VIA,
                        HeaderKeys.KEY_CALL_ID,
                        HeaderKeys.KEY_CSEQ,
                        HeaderKeys.KEY_FROM,
                        HeaderKeys.KEY_TO);
                ctx.writeAndFlush(statusMsg);

            }
        });
    }

}
