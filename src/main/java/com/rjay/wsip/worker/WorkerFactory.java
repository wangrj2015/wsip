package com.rjay.wsip.worker;

import com.rjay.wsip.protocol.msg.SipMsg;
import com.rjay.wsip.worker.message.MessageWorker;
import com.rjay.wsip.worker.proxy.ProxyWorker;
import com.rjay.wsip.worker.registrar.RegisterWorker;
import com.rjay.wsip.protocol.Directive;

/**
 * Created by wangrenjie on 17/3/24.
 */
public class WorkerFactory {

    private static final IWorker defaultWorker = new DefaultWorker();

    private static final IWorker proxyWorker = new ProxyWorker();

    private static final IWorker registyWorker = new RegisterWorker();

    private static final IWorker messageWorker = new MessageWorker();

    public static IWorker getWorker(SipMsg sipMsg){
        String directive = sipMsg.getDirective();
        //注册
        if(directive.equals(Directive.REGISTER.name())){
            return registyWorker;
        }
        //代理
        else if(directive.equals(Directive.INVITE.name())
                || directive.equals(Directive.BYE.name())){
            return proxyWorker;
        }
        //消息
        else if(directive.equals(Directive.MESSAGE.name())){
            return messageWorker;
        }
        return defaultWorker;
    }
}
