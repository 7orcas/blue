package com.sevenorcas.blue.system.rest;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.java.interceptor.RestAuthorisation;

@Interceptors(RestAuthorisation.class)
public class BaseRest {

}
