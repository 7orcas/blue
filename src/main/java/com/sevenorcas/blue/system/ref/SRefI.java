package com.sevenorcas.blue.system.ref;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseEntity;
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Reference Module service interface
*  
* Create 05.12.2022
* [Licence]
* @author John Stewart
*/
@Local
public interface SRefI extends BaseServiceI {
	public JsonRes listJson(CallObject callObj, SqlParm parms, Class<? extends BaseEntityRef<?>> clazz) throws Exception;
	public JsonRes newJson(CallObject callObj, Class<? extends BaseEntityRef<?>> clazz) throws Exception;
//	public JsonRes putReference(CallObject callObj, List<? extends BaseEntity<?>> list, Class<? extends BaseEntityRef<?>> clazz) throws Exception;
//	public JsonRes putReference(CallObject callObj, List<BaseEntity> list, Class<? extends BaseEntityRef<?>> clazz) throws Exception;
	public <T extends BaseEntity<T>> JsonRes putReference(CallObject callObj, List<T> list, Class<? extends BaseEntityRef<?>> clazz) throws Exception;
}
