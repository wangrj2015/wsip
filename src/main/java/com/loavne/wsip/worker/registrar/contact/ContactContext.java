package com.loavne.wsip.worker.registrar.contact;

import com.loavne.wsip.protocol.msg.SipRequestMsg;
import com.loavne.wsip.protocol.msg.SipStatusMsg;
import io.netty.channel.Channel;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class ContactContext {

    private Channel channel;

    private String contact;

    private ContactState contactState;

    private int expires;

    private long expiresMillTimes;

    public ContactContext(String contact) {
        this.contact = contact;
    }

    public SipStatusMsg handle(SipRequestMsg msg){
        return contactState.state(msg);
    }

    public void nextState(){
        if(contactState instanceof ContactStateInit){
            contactState = new ContactStateUnauthorized();
        }else if (contactState instanceof ContactStateUnauthorized){
            contactState = new ContactStateOk();
        }
    }


    public void setContactState(ContactState contactState) {
        this.contactState = contactState;
    }

    public ContactState getContactState() {
        return contactState;
    }

    public long getExpiresMillTimes() {
        return expiresMillTimes;
    }

    public void setExpiresMillTimes(long expiresMillTimes) {
        this.expiresMillTimes = expiresMillTimes;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
