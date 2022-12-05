package com.sevenorcas.blue.app.ref;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.BaseTransferI;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
 * Data access methods for reference data interface
 * 
 * Created 02.12.22
 * [Licence]
 * @author John Stewart
 */

@Local
public interface TRefI extends BaseTransferI {
	//public <T>List<BaseEntityRef<T>> list(CallObject callObj, SqlParm parms, Class<BaseEntityRef<T>> T) throws Exception;
	public <T>List<T> list(CallObject callObj, SqlParm parms, Class<T> T) throws Exception;
}
