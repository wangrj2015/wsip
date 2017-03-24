package com.loavne.wsip.worker;

import com.loavne.wsip.worker.proxy.ProxyWorker;
import com.loavne.wsip.worker.registrar.RegisterWorker;
import com.loavne.wsip.protocol.Directive;
import com.loavne.wsip.protocol.SipRequestMsg;

/**
 * Created by wangrenjie on 17/3/24.
 */
public class WorkerFactory {

    private static final IWorker proxyWorker = new ProxyWorker();

    private static final IWorker registyWorker = new RegisterWorker();

    public static IWorker getWorker(SipRequestMsg sipRequestMsg){
        //注册
        if(sipRequestMsg.getDirective().equals(Directive.REGISTER.name())){
            return registyWorker;
        }
        return proxyWorker;
    }
}
