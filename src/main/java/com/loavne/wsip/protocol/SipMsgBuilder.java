package com.loavne.wsip.protocol;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by wangrenjie on 17/3/24.
 *
 */
public class SipMsgBuilder {

    public static SipMsg build(String message){
        String[] array = message.split("\r\n");
        String line = array[0];
        String[] lineArray = array[0].split(" ");
        SipMsg sipMsg = null;
        Directive directive = Directive.valueOf(lineArray[0]);
        if(null != directive){
            sipMsg = new SipRequestMsg(directive.name(),lineArray[1],lineArray[2]);
        }else{
            sipMsg = new SipStatusMsg(lineArray[0],lineArray[1],lineArray[2]);
        }
        sipMsg.setLine(line);
        Map<String,String> headers = Maps.newHashMap();
        for(int i=1; i<array.length; i++){
            String[] kv = array[i].split(": ");
            headers.put(kv[0],kv[1]);
        }
        sipMsg.setHeaders(headers);
        return sipMsg;
    }
}
