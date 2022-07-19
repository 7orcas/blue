package com.sevenorcas.blue.system.rest;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.java.RestAuthorisation;
import com.sevenorcas.blue.system.java.interceptor.CallObjectInjector;

@Interceptors({RestAuthorisation.class, CallObjectInjector.class})
public class BaseRest {

}
