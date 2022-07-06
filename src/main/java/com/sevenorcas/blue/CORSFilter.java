package com.sevenorcas.blue;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Filter to allow Cross Domain (CORS) access
 * 
 * Cross-Origin Resource Sharing (CORS) is an HTTP-header based mechanism that allows a server to indicate 
 * any origins (domain, scheme, or port) other than its own from which a browser should permit loading resources.
 * 
 * All responses will get CORS authorization
 * 
 * May want to disable this in production server?  
 * 
 * Note this file needs to be in app root.
 * 
 * https://stackoverflow.com/questions/23450494/how-to-enable-cross-domain-requests-on-jax-rs-web-services
 * http://www.mastertheboss.com/web/jboss-web-server/how-to-configure-cors-on-wildfly/
 * 
 * [Licence]
 * @author John Stewart
 */


@Provider
public class CORSFilter implements ContainerResponseFilter {

   @Override
   public void filter(final ContainerRequestContext req,
                      final ContainerResponseContext res) throws IOException {
      res.getHeaders().add("Access-Control-Allow-Origin", "*");
      res.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
      res.getHeaders().add("Access-Control-Allow-Credentials", "true");
      res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      res.getHeaders().add("Access-Control-Max-Age", "1209600");
   }

}

