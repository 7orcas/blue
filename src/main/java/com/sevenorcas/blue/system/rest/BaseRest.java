package com.sevenorcas.blue.system.rest;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.java.interceptor.CallObjectInjector;
import com.sevenorcas.blue.system.java.interceptor.RestAuthorisation;

@Interceptors({RestAuthorisation.class, CallObjectInjector.class})
public class BaseRest {

}
