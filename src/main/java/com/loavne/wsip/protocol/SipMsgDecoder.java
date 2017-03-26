package com.loavne.wsip.protocol;

import com.google.common.collect.Maps;
import com.loavne.wsip.protocol.msg.SipErrorMsg;
import com.loavne.wsip.protocol.msg.SipMsg;
import com.loavne.wsip.protocol.msg.SipRequestMsg;
import com.loavne.wsip.protocol.msg.SipStatusMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class SipMsgDecoder extends MessageToMessageDecoder<ByteBuf>{

    private Logger logger = LoggerFactory.getLogger(SipMsgDecoder.class);

    private Charset charset;

    public SipMsgDecoder(){
        charset = Charset.defaultCharset();
    }

    public SipMsgDecoder(Charset charset){
        this.charset = charset;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        String message = byteBuf.toString(charset);
        logger.debug("receive:{}",message);

        String[] array = message.split("\r\n");
        String line = array[0];
        String[] lineArray = array[0].split(" ");
        if(lineArray.length != 3){
            list.add(new SipErrorMsg());
            return;
        }
        SipMsg sipMsg = null;
        if(Directive.contains(lineArray[0])){
            sipMsg = new SipRequestMsg(lineArray[0],lineArray[1],lineArray[2]);
        }else{
            sipMsg = new SipStatusMsg(lineArray[0],lineArray[1],lineArray[2]);
        }
        sipMsg.setLine(line);
        Map<String,String> headers = Maps.newHashMap();
        boolean hasBody = false;
        for(int i=1; i<array.length; i++){
            if(StringUtils.isEmpty(array[i])){
                hasBody = true;
                continue;
            }
            if(hasBody){
                String body = sipMsg.getBody() == null ? "" : sipMsg.getBody();
                sipMsg.setBody(body + array[i]);
                continue;
            }
            String[] kv = array[i].split(": ");
            headers.put(kv[0],kv[1]);
        }
        sipMsg.setHeaders(headers);
        list.add(sipMsg);
    }
}
