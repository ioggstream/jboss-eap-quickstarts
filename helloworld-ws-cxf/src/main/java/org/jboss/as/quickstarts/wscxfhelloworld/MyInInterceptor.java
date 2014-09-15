/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.wscxfhelloworld;
import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.MessageInfo;


/**
 * The sample implementation of a CXF Interceptor
 * 
 * @author rpolli@redhat.com
 */
public class MyInInterceptor extends AbstractSoapInterceptor  {

	public MyInInterceptor() {
		super(Phase.POST_LOGICAL);
	}

	public void handleFault(SoapMessage message) {
		System.out.println("Handling fault");
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		System.out.println("Handling message" + message);

		// Dump headers
		List <Header> list = message.getHeaders();
		for (Header header : list) {
			System.out.println(header.getName() + " = " + header.toString());
		}

		// Get operation name
		Message inMessage = message.getExchange().getInMessage();
		MessageInfo mi = (MessageInfo) inMessage.get("org.apache.cxf.service.model.MessageInfo");
		String methodName = mi.getOperation().getInputName();	
		System.out.println("Operation name: " + methodName);
	}

}
