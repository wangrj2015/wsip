package com.loavne.wsip.worker.registrar.contact;

import com.google.common.collect.Maps;
import com.loavne.wsip.protocol.msg.SipRequestMsg;
import com.loavne.wsip.protocol.msg.SipStatusMsg;
import com.loavne.wsip.protocol.header.HeaderKeys;
import com.loavne.wsip.user.UserManager;
import com.loavne.wsip.util.Constants;
import com.loavne.wsip.util.MapUtils;
import com.loavne.wsip.util.Md5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by wangrenjie on 17/3/25.
 */
public class ContactStateUnauthorized implements ContactState{

    private Logger logger = LoggerFactory.getLogger(ContactStateUnauthorized.class);

    private static final Pattern PATTERN = Pattern.compile("(.*?)=\"(.*?)\"");

    public SipStatusMsg state(SipRequestMsg msg) {
        String authorization = msg.getHeaders().get(HeaderKeys.KEY_AUTHENTICATION);
        if(null == authorization || !auth(authorization)){
            logger.error("authorization failed");
            ContactContext contactContext = ContactHolder.getContactContext(msg.getContact());
            contactContext.setContactState(new ContactStateInit());
            return contactContext.handle(msg);
        }
        SipStatusMsg result = new SipStatusMsg(msg.getVersion(),"200","OK");
        Map<String,String> headers = result.getHeaders();
        headers.put(HeaderKeys.KEY_CONTENT_LENGTH,"0");
        headers.put(HeaderKeys.KEY_SERVER, Constants.SERVER);
        headers.put(HeaderKeys.KEY_CONTACT, "<" + msg.getContact() + ">;expires=" + Constants.DEFAULT_EXPIRES);

        MapUtils.copy(msg.getHeaders(), headers, (key,value) -> {
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

    private boolean auth(String authorization){
        Map<String,String> map = Maps.newHashMap();
        String[] array = authorization.split(",");
        for(String temp : array){
            Matcher matcher = PATTERN.matcher(temp);
            if(!matcher.matches()){
                continue;
            }
            String key = matcher.group(1);
            String value = matcher.group(2);
            Constants.KEYLIST.forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    if(key.contains(s)){
                        map.put(s,value);
                    }
                }
            });
        }
        try{
            String password = UserManager.getPassWord(map.get(Constants.USERNAME));

            String response = Md5.encode(Md5.encode(map.get(Constants.USERNAME) + ":" +
                    map.get(Constants.REALM) + ":" + password) + ":" + map.get(Constants.NONCE) + ":" + Md5.encode("REGISTER:" + map.get(Constants.URI)));
            if(response.equals(map.get(Constants.RESPONSE))){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
