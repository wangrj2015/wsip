package com.loavne.wsip.protocol;

import com.loavne.wsip.protocol.msg.SipRequestMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by wangrenjie on 17/3/26.
 */
public class SipRequestEncoder extends MessageToByteEncoder<SipRequestMsg> {

    private Logger logger = LoggerFactory.getLogger(SipStatusEncoder.class);

    private Charset charset;

    public SipRequestEncoder(){
        this.charset = Charset.defaultCharset();
    }

    public SipRequestEncoder(Charset charset){
        this.charset = charset;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, SipRequestMsg sipRequestMsg, ByteBuf byteBuf) throws Exception {
        logger.debug("send:{}",sipRequestMsg.toString());
        byteBuf.writeBytes(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(sipRequestMsg.toString()), this.charset));
    }
}
