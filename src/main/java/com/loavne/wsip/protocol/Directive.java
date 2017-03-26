package com.loavne.wsip.protocol;

/**
 * Created by wangrenjie on 17/3/24.
 * 请求指令枚举
 */
public enum Directive {

    /**
     * 注册
     */
    REGISTER,

    /**
     * 呼叫
     */
    INVITE,

    REFER,

    NOTIFY,

    PUBLISH,

    SUBSCRIBE,

    UPDATE,

    INFO,

    MESSAGE,

    OPTIONS,

    CANCEL,

    ACK,

    BYE;

    public static boolean contains(String name){
        for(Directive directive : values()){
            if(directive.name().equals(name)){
                return true;
            }
        }
        return false;
    }


}
