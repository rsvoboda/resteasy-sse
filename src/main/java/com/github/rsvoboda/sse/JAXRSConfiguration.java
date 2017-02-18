package com.github.rsvoboda.sse;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.Set;

/**
 * Configures a JAX-RS endpoint.
 *
 */
//@ApplicationPath("service")
@Provider
public class JAXRSConfiguration extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    @Override
    public Set<Object> getSingletons()
    {
        singletons.add(new SseResource());
        return singletons;
    }
}
