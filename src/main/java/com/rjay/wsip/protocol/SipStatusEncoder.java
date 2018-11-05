package com.rjay.wsip.protocol;

import com.rjay.wsip.protocol.msg.SipStatusMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class SipStatusEncoder extends MessageToByteEncoder<SipStatusMsg>{

    private Logger logger = LoggerFactory.getLogger(SipStatusEncoder.class);

    private Charset charset;

    public SipStatusEncoder(){
        this.charset = Charset.defaultCharset();
    }

    public SipStatusEncoder(Charset charset){
        this.charset = charset;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, SipStatusMsg sipStatusMsg, ByteBuf byteBuf) throws Exception {
        logger.debug("send:{}",sipStatusMsg.toString());
        byteBuf.writeBytes(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(sipStatusMsg.toString()), this.charset));
    }
}
