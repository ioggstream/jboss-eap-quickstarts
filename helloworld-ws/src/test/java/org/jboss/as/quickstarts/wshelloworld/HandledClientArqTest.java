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

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.quickstarts.spring.MyBean;
import org.jboss.as.quickstarts.wshandler.MySoapHandler;
import org.jboss.as.quickstarts.wshelloworld.HelloWorldService;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Simple set of tests for the HelloWorld Web Service to demonstrate accessing the web service using a client
 * 
 * @author lnewson@redhat.com
 */
@RunWith(Arquillian.class)
public class HandledClientArqTest {
    private static final String WEBAPP_RESOURCES = "src/main/resources";
    /**
     * The location of the WebApp source folder so we know where to find the web.xml when deploying using Arquillian.
     */
    private static final String WEBAPP_SRC = "src/main/webapp";
    /**
     * The name of the WAR Archive that will be used by Arquillian to deploy the application.
     */
    private static final String APP_NAME = "jboss-helloworld-ws";
    /**
     * The path of the WSDL endpoint in relation to the deployed web application.
     */
    private static final String WSDL_PATH = "HandledService?wsdl";

    @ArquillianResource
    private URL deploymentUrl;

    private HandledClient client;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, APP_NAME + ".war")
            .addPackage(HelloWorldService.class.getPackage())
            .addPackage(MySoapHandler.class.getPackage())
            .addClass(MyBean.class)
            .addAsManifestResource(new File(WEBAPP_SRC, "META-INF/MANIFEST.MF"))
            .addAsWebInfResource(new File(WEBAPP_RESOURCES,"org/jboss/as/quickstarts/wshelloworld/jaxws-handlers.xml"), "classes/org/jboss/as/quickstarts/wshelloworld/jaxws-handlers.xml" )
            .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
            .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/applicationContext.xml"))
            ;
        
        // And now print its content
        // - the jar name is an UUID 
        boolean verbose = true;
        System.out.println(war.toString(verbose));
        return war;

    }

    @Before
    public void setup() {
        try {
            client = new HandledClient(new URL(deploymentUrl, WSDL_PATH));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHello() {
        System.out.println("[Client] Requesting the WebService to say Hello.");

        // Get a response from the WebService
        final String response = client.sayHello();
        assertEquals( "Hello World!" + MySoapHandler.TOKEN, response);

        System.out.println("[WebService] " + response);

    }

    @Test
    public void testHelloName() {
        System.out.println("[Client] Requesting the WebService to say Hello to John.");

        // Get a response from the WebService
        final String response = client.sayHelloToName("John");
        assertEquals("Hello John!" + MySoapHandler.TOKEN, response);

        System.out.println("[WebService] " + response);
    }

    @Test
    public void testHelloNames() {
        System.out.println("[Client] Requesting the WebService to say Hello to John, Mary and Mark.");

        // Create the array of names for the WebService to say hello to.
        final List<String> names = new ArrayList<String>();
        names.add("John");
        names.add("Mary");
        names.add("Mark");

        // Get a response from the WebService
        final String response = client.sayHelloToNames(names);
        assertEquals("Hello John, Mary & Mark!" + MySoapHandler.TOKEN, response);

        System.out.println("[WebService] " + response);
    }
}
