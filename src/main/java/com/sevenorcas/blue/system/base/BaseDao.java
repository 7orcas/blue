package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.lifecycle.DaoAroundInvoke;

@Interceptors({DaoAroundInvoke.class})
public class BaseDao {

}
