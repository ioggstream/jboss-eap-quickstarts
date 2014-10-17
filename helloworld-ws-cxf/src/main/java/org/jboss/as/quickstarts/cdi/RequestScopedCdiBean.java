package org.jboss.as.quickstarts.cdi;

import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScopedCdiBean
{
    private static final Logger LOG = Logger.getLogger(RequestScopedCdiBean.class.getName());

    private String value;

    @PostConstruct
    protected void init()
    {
        this.value = UUID.randomUUID().toString();
        LOG.info("init");
    }

    public String getValue()
    {
        return value;
    }
}