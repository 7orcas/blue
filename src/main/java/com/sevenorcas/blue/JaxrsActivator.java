package com.sevenorcas.blue;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * To deploy the application, we need a JAX-RS class activator.
 * This is a class extending javax.ws.rs.core.Application and 
 * declaring the Path where JAX-RS Services will be available
 * 
 * http://www.mastertheboss.com/jboss-frameworks/resteasy/resteasy-tutorial/
 *
 * [Licence]
 * @author John Stewart
 */

@ApplicationPath("/api")
public class JaxrsActivator extends Application {

}
