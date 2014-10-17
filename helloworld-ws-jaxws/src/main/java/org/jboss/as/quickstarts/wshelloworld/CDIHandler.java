package org.jboss.as.quickstarts.wshelloworld;

import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.jboss.as.quickstarts.cdi.MyBean;



/**
 soap call is departing.
 * 
 * Add this to the default handler-chain via the
 *  jboss-cli 
 *  $ cd /subsystem=webservices/endpoint-config=Standard-Endpoint-Config/
 *  $ /pre-handler-chain=my-handlers:add(protocol-bindings="##SOAP11_HTTP")
 *  $ /pre-handler-chain=my-handlers/handler=addtag-handler:add(class="org.jboss.as.quickstarts.wshandler.LoggingHandler")
 *  $ :reload
 * 
 * @author rpolli@redhat.com
 *
 */
public class CDIHandler implements SOAPHandler<SOAPMessageContext> {
    Logger log = Logger.getLogger(getClass().getName());


    @Inject
    MyBean bean;

    @Override
    public boolean handleMessage(SOAPMessageContext mCtx) {
        if (bean instanceof MyBean)
            return true;
        throw new RuntimeException("Injection doesn't work");
    }
    @Override
    public Set<QName> getHeaders() {
        return null;
    }
    @Override
    public void close(MessageContext mCtx) {

    }
    @Override
    public boolean handleFault(SOAPMessageContext mCtx) {
        return true;    
    }


}
