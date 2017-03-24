package com.loavne.wsip.protocol;

import java.util.Map;

/**
 * Created by wangrenjie on 17/3/24.
 */
public abstract class SipMsg {

    protected String line;

    protected Map<String,String> headers;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
