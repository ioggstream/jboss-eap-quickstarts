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
package org.jboss.as.quickstarts.rshelloworld;

import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled
 * 
 * @author gbrey@redhat.com
 * 
 */

@Path("/")
public class HelloWorld extends SpringBeanAutowiringSupport {
	/* The HelloService is injected by jboss CDI thanks to the WEB-INF/beans.xml */
    @Inject
    HelloService helloService;

	/* The springContext is injected via Spring extending SpringBeanAutowiringSupport and WEB-INF/web.xml:listener-class */
    @Autowired
    ApplicationContext springContext;
    
    /* The myBean is defined in WEB-INF/applicationContext.xml */
    @Autowired
    MyBean myBean;
    
    /*
     * This method returns an HashMap ppopulated with Spring context,
     *  which is nicely serialized by jax-rs thanks to the @Produces 
     *  annotation, describing the content-type of the response. 
     */
    @GET
    @Path("/spring")
    @Produces({ MediaType.APPLICATION_JSON })
    public HashMap<String, String> getSpringContextJSON() {
    	assert myBean != null: "Missing Spring context";
    	
    	HashMap<String, String> ret = new HashMap<String, String>();
    	ret.put("springBean",myBean.getValue());
    	ret.put("springContext", springContext.toString());
    	ret.put("CDI",helloService.createHelloMessage("World"));
    	return ret;
    }
    
	/*
	 * If you don't want to rely on jaxrs serialization, you can
	 *  always return a string!
	 */
    @GET
    @Path("/json")
    @Produces({ MediaType.APPLICATION_JSON })
    public String getHelloWorldJSON() {
    	return "{\"result\":\"" +  helloService.createHelloMessage("World") + "\"}";
    }

    /*
     * You can even specify the content-type directly instead
     *  of using the MediaType constants
     */
    @GET
    @Path("/xml")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
    	assert springContext != null: "Missing Spring context";
        return "<xml><result>" + helloService.createHelloMessage("World") + "</result></xml>";
    }

}
