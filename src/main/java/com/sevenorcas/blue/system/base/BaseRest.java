package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.lifecycle.RestAroundInvoke;

@Interceptors({RestAroundInvoke.class})
public class BaseRest {

}
