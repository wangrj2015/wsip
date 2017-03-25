package com.loavne.wsip.protocol;

import com.loavne.wsip.protocol.header.HeaderKeys;

/**
 * Created by wangrenjie on 17/3/24.
 */
public class SipRequestMsg extends SipMsg{

    private String directive;

    private String address;

    private String version;

    public SipRequestMsg(String directive, String address, String version) {
        this.directive = directive;
        this.address = address;
        this.version = version;
    }

    public String getDirective() {
        return directive;
    }

    public void setDirective(String directive) {
        this.directive = directive;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContact(){
        String contact = this.getHeaders().get(HeaderKeys.KEY_CONTACT);
        String[] array = contact.split(";\\+");
        return array[0];
    }
}
