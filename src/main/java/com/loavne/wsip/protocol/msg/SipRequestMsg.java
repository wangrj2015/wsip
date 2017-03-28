package com.loavne.wsip.protocol.msg;

import com.loavne.wsip.protocol.header.HeaderKeys;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

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
        String contact = this.getHeaders().get(HeaderKeys.KEY_TO);
        if(contact.contains("<")){
            contact = contact.substring(contact.indexOf("<") + 1, contact.indexOf(">"));
        }
        return contact;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(directive + " " + address + " " + version + "\r\n");
        for(Map.Entry<String,String> entry : headers.entrySet()){
            sb.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        sb.append("\r\n");
        if(StringUtils.isNotEmpty(body)){
            sb.append(body);
        }
        return sb.toString();
    }
}
