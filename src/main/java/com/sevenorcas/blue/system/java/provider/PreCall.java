package com.sevenorcas.blue.system.java.provider;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;


@PreMatching //indicates (with request filter) that such filter should be applied globally on all resources in the application before the actual resource matching occurs

@Provider
public class PreCall implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(final ContainerRequestContext req) throws IOException {

System.out.println(">> PreCall filter <<");
	
	}

	
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
System.out.println(">> PostCall filter <<");
		
	}
	 
}