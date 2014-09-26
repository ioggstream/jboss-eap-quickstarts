package org.jboss.as.quickstarts.wshandler;

import java.util.Calendar;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;



/**
 * A SoapHandler is invoked twice for every transaction:
 *  - when a soap call is incoming;
 *  - when a soap call is departing.
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
	}

	/**
	 * A Fault handler is run in case of faults during the processing chain.
	 * 
	 * @return true if we should continue on the handler chain, false otherwise.
	 */
	@Override
	public boolean handleFault(SOAPMessageContext mCtx) {
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
		Boolean outbound = (Boolean) mCtx
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		Calendar cal = Calendar.getInstance();
		
		if (outbound) {
			Long elapsed = cal.getTimeInMillis() - (Long) mCtx.get("time");
			log.warning("Elapsed time: " + elapsed);
		} else { 
			mCtx.put("time", (Long) cal.getTimeInMillis());
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
