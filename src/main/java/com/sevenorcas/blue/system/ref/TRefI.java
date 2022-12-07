package com.sevenorcas.blue.system.ref;

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
	public List<? extends BaseEntityRef<?>> list(CallObject callObj, SqlParm parms, Class<? extends BaseEntityRef<?>> T) throws Exception;
	public void resetDvalues(CallObject callObj, Class<? extends BaseEntityRef<?>> T) throws Exception;
}
