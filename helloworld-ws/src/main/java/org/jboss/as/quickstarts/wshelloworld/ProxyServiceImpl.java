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
package org.jboss.as.quickstarts.wshelloworld;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

/**
 * The implementation of a Proxy JAX-WS Web Service
 *  to the HelloWorldImpl
 * 
 * @author lnewson@redhat.com
 */
@WebService(serviceName = "ProxyService", 
	portName = "Proxy", 
	name = "Proxy", 
	endpointInterface = "org.jboss.as.quickstarts.wshelloworld.HelloWorldService",
	targetNamespace = "http://www.jboss.org/jbossas/quickstarts/wshelloworld/Proxy")
public class ProxyServiceImpl implements HelloWorldService  {
    /**
     * The name of the WAR Archive that will be used by Arquillian to deploy the application.
     */
    private static final String APP_NAME = "jboss-helloworld-ws";
    
    /**
     * The Default Server URL if one isn't specified as a System Property
     */
    private static final String DEFAULT_SERVER_URL = "http://localhost:8080/";
    
	private Client getClient(){
        try {
            return new Client(new URL(DEFAULT_SERVER_URL + "/" + APP_NAME + "/?wsdl"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public String sayHello() {
		return getClient().sayHello();
	}

	public String sayHelloToName(final String name) {
		return getClient().sayHelloToName(name);
	}

	public String sayHelloToNames(final List<String> names) {
		return getClient().sayHelloToNames(names);
	}
}
