package com.rjay.wsip.protocol;

import com.google.common.collect.Maps;
import com.rjay.wsip.protocol.header.HeaderKeys;
import com.rjay.wsip.protocol.msg.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class SipMsgDecoder extends LineBasedFrameDecoder {

    private Logger logger = LoggerFactory.getLogger(SipMsgDecoder.class);

    private Charset charset;

    public SipMsgDecoder(int maxLength){
        super(maxLength);
        charset = Charset.defaultCharset();
    }

    public SipMsgDecoder(int maxLength, boolean stripDelimiter, boolean failFast){
        super( maxLength, stripDelimiter, failFast);
        charset = Charset.defaultCharset();
    }

    public SipMsgDecoder(int maxLength, boolean stripDelimiter, boolean failFast,Charset charset){
        super( maxLength, stripDelimiter, failFast);
        this.charset = charset;
    }

    private Map<String,MsgBuffer> msgBufferMap = Maps.newHashMap();

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        MsgBuffer msgBuffer = buffer(ctx,byteBuf);
        if(null == msgBuffer){
            return null;
        }
        String message = msgBuffer.toString();
        logger.debug("receive:{}",message);

        String[] array = msgBuffer.getHeaders().toString().split("\r\n");
        String line = array[0];
        String[] lineArray = array[0].split(" ");
        if(lineArray.length != 3){
            return new SipErrorMsg();
        }
        SipMsg sipMsg = null;
        if(Directive.contains(lineArray[0])){
            sipMsg = new SipRequestMsg(lineArray[0], lineArray[1], lineArray[2]);
        }else{
            sipMsg = new SipStatusMsg(lineArray[0], lineArray[1], lineArray[2]);
        }
        sipMsg.setLine(line);
        Map<String,String> headers = Maps.newHashMap();
        for(int i=1; i<array.length; i++){
            if(StringUtils.isEmpty(array[i])){
                continue;
            }
            String[] kv = array[i].split(": ");
            headers.put(kv[0],kv[1]);
        }
        sipMsg.setHeaders(headers);
        sipMsg.setBody(msgBuffer.getBody().toString());
        msgBuffer.clear();
        return sipMsg;
    }

    private MsgBuffer buffer(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception{
        String channelId = ctx.channel().id().asShortText();
        MsgBuffer msgBuffer = msgBufferMap.get(channelId);
        if(null == msgBuffer){
            msgBuffer = new MsgBuffer();
            msgBufferMap.put(channelId,msgBuffer);
        }

        ByteBuf temp = null;
        if(msgBuffer.isReadBody()){
            if(msgBuffer.getContentLength() == 0 || !byteBuf.isReadable()
                    || byteBuf.readableBytes() < msgBuffer.getContentLength()){
                return null;
            }
            temp = byteBuf.readRetainedSlice(msgBuffer.getContentLength());
        }else{
            temp = (ByteBuf) super.decode(ctx,byteBuf);
        }
        if(null == temp){
            return null;
        }
        String message = temp.toString(charset);
        if(!message.equals("\r\n")){
            if(msgBuffer.isReadBody()){
                msgBuffer.appendBody(message);
                msgBufferMap.put(channelId,null);
            }else{
                msgBuffer.appendHeaders(message);
                if(message.contains(HeaderKeys.KEY_CONTENT_LENGTH)){
                    msgBuffer.setContentLength(Integer.parseInt(message.split(": ")[1].replace("\r\n","")));
                }
                return null;
            }
        }else{
            msgBuffer.append(message);
            if(msgBuffer.getContentLength() != 0){
                msgBuffer.setReadBody(true);
                return null;
            }
            msgBufferMap.put(channelId,null);
        }
        return msgBuffer;
    }
}
