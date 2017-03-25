package com.loavne.wsip.worker.registrar.contact;

import com.loavne.wsip.protocol.SipRequestMsg;
import com.loavne.wsip.protocol.SipStatusMsg;

/**
 * Created by wangrenjie on 17/3/25.
 */
public interface ContactState {

    SipStatusMsg state(SipRequestMsg msg);
}
