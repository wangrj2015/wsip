package com.loavne.wsip.protocol.msg;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by wangrenjie on 17/3/24.
 */
public abstract class SipMsg {

    protected String line;

    protected Map<String,String> headers = Maps.newHashMap();

    protected String body;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public abstract String getDirective();
}
