package com.loavne.wsip.protocol.msg;

import java.util.Map;

/**
 * Created by wangrenjie on 17/3/24.
 */
public class SipStatusMsg extends SipMsg{

    private String version;

    private String statusCode;

    private String statusName;

    public SipStatusMsg(String version, String statusCode, String statusName) {
        this.version = version;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(version + " " + statusCode + " " + statusName + "\r\n");
        for(Map.Entry<String,String> entry : headers.entrySet()){
            sb.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
