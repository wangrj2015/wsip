package com.loavne.wsip.protocol;

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
}
