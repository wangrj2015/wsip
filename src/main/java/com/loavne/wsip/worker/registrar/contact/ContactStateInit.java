package com.loavne.wsip.worker.registrar.contact;

import com.google.common.collect.Maps;
import com.loavne.wsip.protocol.SipRequestMsg;
import com.loavne.wsip.protocol.SipStatusMsg;
import com.loavne.wsip.protocol.header.HeaderKeys;
import com.loavne.wsip.util.MapUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class ContactStateInit implements ContactState {

    public SipStatusMsg state(SipRequestMsg msg) {
        SipStatusMsg result = new SipStatusMsg(msg.getVersion(),"401","Unauthorized");
        Map<String,String> headers = Maps.newHashMap();
        headers.put(HeaderKeys.KEY_CONTENT_LENGTH,"0");
        headers.put(HeaderKeys.KEY_SERVER,"wsip/1.0 (wangrenjie)");
        headers.put(HeaderKeys.KEY_WWW_AUTHENTICATE,
                "Digest realm=\"127.0.0.1\",nonce=\"" + System.currentTimeMillis() + "\"");

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

        result.setHeaders(headers);
        return result;
    }
}
