package com.sevenorcas.blue.system.org;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseTransferI;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.DtoOrg;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Organisation data access interface
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

@Local
public interface TOrgI extends BaseTransferI {
	public List<DtoOrg> orgList(CallObject callObj, SqlParm parms) throws Exception;
}
