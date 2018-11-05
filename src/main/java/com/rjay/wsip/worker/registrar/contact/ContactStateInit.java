package com.rjay.wsip.worker.registrar.contact;

import com.rjay.wsip.protocol.msg.SipRequestMsg;
import com.rjay.wsip.protocol.msg.SipStatusMsg;
import com.rjay.wsip.protocol.header.HeaderKeys;
import com.rjay.wsip.util.Constants;
import com.rjay.wsip.util.MapUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class ContactStateInit implements ContactState {

    public SipStatusMsg state(SipRequestMsg msg) {
        SipStatusMsg result = new SipStatusMsg(msg.getVersion(),"401","Unauthorized");
        Map<String,String> headers = result.getHeaders();
        headers.put(HeaderKeys.KEY_CONTENT_LENGTH, "0");
        headers.put(HeaderKeys.KEY_SERVER, Constants.SERVER);
        headers.put(HeaderKeys.KEY_WWW_AUTHENTICATE,
                "Digest realm=\"127.0.0.1\",nonce=\"" + System.currentTimeMillis() + "\"");

        MapUtils.copy(msg.getHeaders(), headers, (key, value) -> {
                    //To
                    if(HeaderKeys.KEY_TO.equals(key)){
                        return "<" + value + ">;tag=" + UUID.randomUUID().toString().replaceAll("-","");
                    }
                    return value;
                }, HeaderKeys.KEY_VIA,
                      HeaderKeys.KEY_CALL_ID,
                      HeaderKeys.KEY_CSEQ,
                      HeaderKeys.KEY_FROM,
                      HeaderKeys.KEY_TO);

        return result;
    }
}
