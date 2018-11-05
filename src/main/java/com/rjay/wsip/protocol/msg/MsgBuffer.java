package com.rjay.wsip.protocol.msg;

/**
 * Created by wangrenjie on 17/3/27.
 */
public class MsgBuffer {

    private StringBuffer sb = new StringBuffer();

    private StringBuffer headers = new StringBuffer();

    private StringBuffer body = new StringBuffer();

    private int contentLength;

    private boolean readBody = false;

    public MsgBuffer() {
    }

    public MsgBuffer(String str){
        sb.append(str);
        headers.append(str);
    }

    public StringBuffer append(String str){
        return sb.append(str);
    }

    public StringBuffer appendHeaders(String str){
        sb.append(str);
        return headers.append(str);
    }

    public StringBuffer appendBody(String str){
        sb.append(str);
        return body.append(str);
    }

    public void clear(){
        sb = new StringBuffer();
        headers = new StringBuffer();
        body = new StringBuffer();
        contentLength = 0;
    }

    public StringBuffer getSb() {
        return sb;
    }

    public void setSb(StringBuffer sb) {
        this.sb = sb;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isReadBody() {
        return readBody;
    }

    public void setReadBody(boolean readBody) {
        this.readBody = readBody;
    }

    public StringBuffer getHeaders() {
        return headers;
    }

    public void setHeaders(StringBuffer headers) {
        this.headers = headers;
    }

    public StringBuffer getBody() {
        return body;
    }

    public void setBody(StringBuffer body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
