package org.jboss.as.quickstarts.wshandler;
/**
 * A SoapHandler does something on incoming/outgoing soap calls
 */
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;




public class MySoapHandler implements SOAPHandler<SOAPMessageContext> {
	Logger log = Logger.getLogger("org.jboss.as.quickstarts.wshandler");
	SOAPFactory factory;

	public MySoapHandler() {
		try {
			factory = SOAPFactory.newInstance();
		} catch (SOAPException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is the last-invoked method
	 */
	@Override
	public void close(MessageContext mCtx) {
		log.warning("MySoapHandler.close()");	
	}

	/**
	 * @return true if we should continue on the handler chain, false otherwise.
	 */
	@Override
	public boolean handleFault(SOAPMessageContext mCtx) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext mCtx) {
		SOAPMessage msg = mCtx.getMessage();
		log.warning("MySoapHandler: print msg:" + msg);

		Boolean outbound = (Boolean) mCtx
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		SOAPBody body;
		SOAPHeader header;
		try {
			SOAPFactory factory = SOAPFactory.newInstance();
			body = msg.getSOAPBody();
			header = msg.getSOAPHeader();


			if (outbound) { 
				log.warning("SOAP message departing…");
				SOAPElement e = factory.createElement(new QName("From", HandledServiceImpl.NS0));
				e.addTextNode((String) mCtx.get("tag"));
				header.addChildElement(e);
			} else { 
				log.warning("SOAP message incoming…"); 
				mCtx.put("headers", (Object) header.getChildNodes().item(0));
				mCtx.put("tag", mCtx.getRoles().toString());
			} 

		} catch (SOAPException e) {
			throw new RuntimeException("MySoapHandler throws:", e);
		}
		log.warning("MySoapHandler: print msg:" + body);
		return true; // process following handlers
	}

	/**
	 * This is the first-invoked method
	 */
	@Override
	public Set<QName> getHeaders() {
		log.warning("MySoapHandler.getHeader()");
		return null;
	}

}
