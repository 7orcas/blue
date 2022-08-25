package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.lifecycle.SrvAroundInvoke;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({SrvAroundInvoke.class})
public class BaseSrv extends BaseUtil {

	public String cleanParam (String s) {
		return s == null? "" : s;
	}
}
