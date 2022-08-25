package com.sevenorcas.blue.system.base;

import javax.interceptor.Interceptors;

import com.sevenorcas.blue.system.AppProperties;
import com.sevenorcas.blue.system.lifecycle.DaoAroundInvoke;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Created Jul '22
* 
* TODO Module Description
* 
* [Licence]
* @author John Stewart
*/

@Interceptors({DaoAroundInvoke.class})
public class BaseDao extends BaseUtil {
	
	/**
	 * Ensure a valid SQL Parameter object is used
	 * @param parms
	 * @return
	 */
	protected SqlParm validateParms (SqlParm parms) {
		if (parms == null) {
			return new SqlParm ();
		}
		return parms;
	}
	
}
