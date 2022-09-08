package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.lang.HardCodeLangKeyI;
import com.sevenorcas.blue.system.lifecycle.SrvAroundInvoke;
import com.sevenorcas.blue.system.util.JsonResponseI;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({SrvAroundInvoke.class})
public class BaseSrv extends BaseUtil implements HardCodeLangKeyI, JsonResponseI {

	public String cleanParam (String s) {
		return s == null? "" : s;
	}
}
