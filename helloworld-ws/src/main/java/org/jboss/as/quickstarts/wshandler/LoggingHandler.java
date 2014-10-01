package org.jboss.as.quickstarts.wshandler;

import java.util.Calendar;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;



/**
 * A SoapHandler is invoked twice for every transaction:
 *  - when a soap call is incoming;
 *  - when a soap call is departing.
 * 
 * You can add this to the default handler-chain via the
 *  jboss-cli 
 *  $ cd /subsystem=webservices/endpoint-config=Standard-Endpoint-Config/
 *  $ /pre-handler-chain=my-handlers:add(protocol-bindings="##SOAP11_HTTP")
 *  $ /pre-handler-chain=my-handlers/handler=addtag-handler:add(class="org.jboss.as.quickstarts.wshandler.LoggingHandler")
 *  $ :reload
 * 
 * @author rpolli@redhat.com
 *
 */
public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
    Logger log = Logger.getLogger("org.jboss.as.quickstarts.wshandler");

    /**
     * This method is called only once, at the 
     * second invocation of the handler.
     * 
     * For Endpoint handlers it is called during the 
     *  outbound, while for Client handlers for the inbound.
     */
    @Override
    public void close(MessageContext mCtx) {
        // Cleanup the mCtx. This is done here
        // so the mCtx is not emptied unless we've finished
        // to play.
        mCtx.remove("time");
    }

    /**
     * A Fault handler is run in case of faults during the processing chain.
     *  - this just dumps the message to stderr
     * 
     * @return true if we should continue on the handler chain, false otherwise.
     */
    @Override
    public boolean handleFault(SOAPMessageContext mCtx) {
        try {
            SOAPMessage msg = mCtx.getMessage();
            msg.writeTo(System.err);
        }
        catch(Exception e) { throw new RuntimeException(e); }
        return true;    
    }

    /**
     * Here we can modify the message or doing various actions.
     *  
     *  @param mCtx - a sharedcontext between inbound and outbound call. 
     *  @return true if we need to process other handlers, false otherwise
     *  
     */
    @Override
    public boolean handleMessage(SOAPMessageContext mCtx) {
        Calendar cal = Calendar.getInstance();

        if (mCtx.get("time") == null) {
            HttpServletRequest httpServletRequest = (HttpServletRequest)mCtx.get(MessageContext.SERVLET_REQUEST);
            if (httpServletRequest != null) {
                String length = httpServletRequest.getHeader("content-length");
            }
            mCtx.put("time", (Long) cal.getTimeInMillis());
        } else  {
            QName op = (QName)mCtx.get(MessageContext.WSDL_OPERATION);
            Long elapsed = cal.getTimeInMillis() - (Long) mCtx.get("time");
            log.warning("Elapsed time for " + op.getLocalPart().toString()  +": " +  elapsed);
        } 
        return true; // process following handlers
    }

    /**
     * This method is invoked first on both incoming/outgoing calls
     */
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}
