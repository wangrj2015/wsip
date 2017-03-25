package com.loavne.wsip.protocol;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class SipRequestDecoder extends MessageToMessageDecoder<ByteBuf>{

    private Logger logger = LoggerFactory.getLogger(SipRequestDecoder.class);

    private Charset charset;

    public SipRequestDecoder(){
        charset = Charset.defaultCharset();
    }

    public SipRequestDecoder(Charset charset){
        this.charset = charset;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String message = byteBuf.toString(charset);
        logger.debug(message);

        String[] array = message.split("\r\n");
        String line = array[0];
        String[] lineArray = array[0].split(" ");
        Directive directive = Directive.valueOf(lineArray[0]);
        SipRequestMsg sipMsg = new SipRequestMsg(directive.name(),lineArray[1],lineArray[2]);
        sipMsg.setLine(line);
        Map<String,String> headers = Maps.newHashMap();
        for(int i=1; i<array.length; i++){
            String[] kv = array[i].split(": ");
            headers.put(kv[0],kv[1]);
        }
        sipMsg.setHeaders(headers);
        list.add(sipMsg);
    }
}
