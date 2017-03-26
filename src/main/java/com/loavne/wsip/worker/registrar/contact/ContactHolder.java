package com.loavne.wsip.worker.registrar.contact;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class ContactHolder {

    private static Logger logger = LoggerFactory.getLogger(ContactHolder.class);

    private static final ConcurrentMap<String,ContactContext> contactContextMap = Maps.newConcurrentMap();

    public static ContactContext getContactContext(String contact){
        ContactContext context = contactContextMap.get(contact);
        if(null == context){
            return null;
        }
        if(System.currentTimeMillis() > context.getExpiresMillTimes()){
            logger.warn("Contact:{} expired",contact);
            contactContextMap.remove(contact);
            return null;
        }
        return context;
    }

    public static ContactContext getOkContactContext(String contact){
        ContactContext contactContext = getContactContext(contact);
        if(null == contactContext){
            return null;
        }
        if(contactContext.getContactState() instanceof ContactStateOk){
            return contactContext;
        }
        return null;
    }

    public static void putContactContext(String contact,ContactContext context){
        contactContextMap.putIfAbsent(contact,context);
    }


}
