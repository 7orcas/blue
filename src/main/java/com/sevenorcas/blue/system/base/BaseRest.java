package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.ApplicationI;
import com.sevenorcas.blue.system.lang.HardCodeLangKeyI;
import com.sevenorcas.blue.system.lifecycle.RestAroundInvoke;
import com.sevenorcas.blue.system.util.JsonResponseI;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({RestAroundInvoke.class})
public class BaseRest extends BaseUtil implements HardCodeLangKeyI, JsonResponseI {
}
