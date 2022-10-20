package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.lang.IntHardCodeLangKey;
import com.sevenorcas.blue.system.lifecycle.RestAroundInvoke;
import com.sevenorcas.blue.system.util.JsonResponseI;

/**
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({RestAroundInvoke.class})
public class BaseRest extends BaseUtil implements IntHardCodeLangKey, JsonResponseI {
	
	
//	/**
//	 * Return an entity's configuration
//	 * 
//	 * @param callObj
//	 * @param org id
//	 * @return
//	 * @throws Exception
//	 */
//	@GET
//	@Path("config")
//    public JsonRes getConfig(
//    		@QueryParam ("co") CallObject callObj,
//    		@QueryParam ("entity") String entity) throws Exception {
//		if (entity == null) {
//			return new JsonRes().setError(LK_UNKNOWN_ERROR, "Invalid entity");
//		}
//		return serviceConf.getConfigJson(callObj, entity);
//    }
	
}
