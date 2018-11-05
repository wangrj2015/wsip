package com.rjay.wsip.worker.registrar.contact;

import com.rjay.wsip.protocol.msg.SipRequestMsg;
import com.rjay.wsip.protocol.msg.SipStatusMsg;

/**
 * Created by wangrenjie on 17/3/25.
 */
public interface ContactState {

    SipStatusMsg state(SipRequestMsg msg);
}
