package com.sevenorcas.blue.app.ref;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* User Module service interface
*  
* Create 01.11.2022
* [Licence]
* @author John Stewart
*/
@Local
public interface SRefI extends BaseServiceI {
	public JsonRes listCountryJson(CallObject callObj, SqlParm parms) throws Exception;
	public JsonRes newCountryJson(CallObject callObj) throws Exception;
	public JsonRes listCurrencyJson(CallObject callObj, SqlParm parms) throws Exception;
}
