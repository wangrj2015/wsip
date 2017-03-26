package com.loavne.wsip.worker.registrar.contact;

import com.loavne.wsip.protocol.msg.SipRequestMsg;
import com.loavne.wsip.protocol.msg.SipStatusMsg;

/**
 * Created by wangrenjie on 17/3/25.
 */
public class ContactStateOk implements ContactState{

    public SipStatusMsg state(SipRequestMsg msg) {
        return null;
    }
}
