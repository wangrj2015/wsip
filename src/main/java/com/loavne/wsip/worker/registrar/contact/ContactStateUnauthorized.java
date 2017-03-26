package com.loavne.wsip.worker.registrar.contact;

import com.loavne.wsip.protocol.msg.SipRequestMsg;
import com.loavne.wsip.protocol.msg.SipStatusMsg;
import com.loavne.wsip.protocol.header.HeaderKeys;
import com.loavne.wsip.util.Constants;
import com.loavne.wsip.util.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;


/**
 * Created by wangrenjie on 17/3/25.
 */
public class ContactStateUnauthorized implements ContactState{

    private Logger logger = LoggerFactory.getLogger(ContactStateUnauthorized.class);

    public SipStatusMsg state(SipRequestMsg msg) {
        String authorization = msg.getHeaders().get(HeaderKeys.KEY_AUTHENTICATION);
        if(null == authorization){
            logger.error("authorization is null");
            return null;
        }
        if(!auth(authorization)){
            logger.error("authorization invalid");
            return null;
        }
        SipStatusMsg result = new SipStatusMsg(msg.getVersion(),"200","OK");
        Map<String,String> headers = result.getHeaders();
        headers.put(HeaderKeys.KEY_CONTENT_LENGTH,"0");
        headers.put(HeaderKeys.KEY_SERVER, Constants.SERVER);
        headers.put(HeaderKeys.KEY_CONTACT, "<" + msg.getContact() + ">;expires=" + Constants.DEFAULT_EXPIRES);

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

        return result;

    }

    private boolean auth(String authorization){
        return true;
    }
}
