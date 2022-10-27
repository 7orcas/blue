package com.sevenorcas.blue.system.base;

import javax.interceptor.InvocationContext;

import com.sevenorcas.blue.system.lifecycle.CallObject;

/**
 * Base Around Invoke Object 
 * Contains utility functions
 * 
 * Create 27.10.2022
 * Licence
 * @author John Stewart
 */
public class BaseAroundInvoke {

	protected CallObject getCallObject (InvocationContext ctx) {
		try {
			for (int i=0;i<ctx.getMethod().getParameterTypes().length;i++) {
				if (ctx.getMethod().getParameterTypes()[i].getTypeName().equals(CallObject.class.getTypeName())) {
					return (CallObject)ctx.getParameters()[i];
				}
			}
		} catch (Exception x) {
		}
		return null;
	}
	
}
