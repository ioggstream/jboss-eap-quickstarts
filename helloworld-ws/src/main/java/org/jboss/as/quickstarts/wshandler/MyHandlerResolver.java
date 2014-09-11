package org.jboss.as.quickstarts.wshandler;
/**
 * An HandlerResolver associates an handler chain to a given portInfo.
 * 
 * The webservice can use the @HandlerChain decorator to set the handlers
 *  and avoid implementing HandlerResolver
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class MyHandlerResolver implements HandlerResolver {

	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		System.out.println("getHandlerChain: " + portInfo);
		List<Handler> handlerChain = new ArrayList<Handler>();
		QName serviceQName = portInfo.getServiceName();
		System.out.println("getHandlerChain.serviceQName: " + serviceQName);
		
		// add the handler on given conditions
		if(serviceQName.getLocalPart().equals("Amadeus")) {
			handlerChain.add(new MySoapHandler());
		}
		handlerChain.add(new MySoapHandler());
		
		System.out.println("getHandlerChain returns: " + handlerChain);
		return handlerChain;
	}

}
